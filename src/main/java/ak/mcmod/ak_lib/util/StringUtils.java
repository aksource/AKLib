package ak.mcmod.ak_lib.util;

import com.google.common.base.Joiner;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by A.K. on 2018/10/14.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class StringUtils {

  private static final Joiner AT_JOINER = Joiner.on('@');
  private static final Function<Entry<Property<?>, Comparable<?>>, String> functionBlockStateBase = ObfuscationReflectionHelper.getPrivateValue(StateHolder.class, null, "f_61110_");

  public static boolean isEmpty(CharSequence charSequence) {
    return org.apache.commons.lang3.StringUtils.isEmpty(charSequence);
  }

  @SuppressWarnings("unused")
  public static boolean isNotEmpty(CharSequence charSequence) {
    return !isEmpty(charSequence);
  }

  /**
   * {@code ResourceLocation}から固有文字列を取得
   *
   * @param resourceLocation ResourceLocation
   * @return 固有文字列
   */
  public static String getUniqueString(@Nullable ResourceLocation resourceLocation) {
    return Objects.nonNull(resourceLocation) ? resourceLocation.toString() : "";
  }

  public static List<String> makeStringDataFromBlockState(BlockState state) {
    var block = state.getBlock();
    var itemStack = new ItemStack(block, 1);
    var tagManager = ForgeRegistries.BLOCKS.tags();
    if (itemStack.getItem() != Items.AIR && Objects.nonNull(tagManager)) {
      var tagOptional = tagManager.getReverseTag(block);
      var stringList = new ArrayList<String>();
      tagOptional.ifPresent(tag -> stringList.addAll(tag.getTagKeys().map(TagKey::location).map(ResourceLocation::toString).toList()));
      stringList.add(makeString(state));
      return stringList;
    }
    return Collections.singletonList(makeString(state));
  }

  private static String makeString(BlockState state) {
    var stringBuilder = new StringBuilder();
    var key = ForgeRegistries.BLOCKS.getKey(state.getBlock());
    if (Objects.nonNull(key)) {
      stringBuilder.append(key);
    }

    if (!state.getProperties().isEmpty()) {
      stringBuilder.append("[");
      assert functionBlockStateBase != null;
      AT_JOINER.appendTo(stringBuilder, state.getValues().entrySet().stream().map(functionBlockStateBase).collect(Collectors.toList()));
      stringBuilder.append("]");
    }

    return stringBuilder.toString();
  }

  /**
   * 破壊対象ブロック名集合内に固有文字列が含まれているかどうか
   *
   * @param set     破壊対象ブロック名集合
   * @param uid     ブロックの固有文字列
   * @param uidMeta メタ付きブロックの固有文字列　[固有文字列]:[meta]
   * @return 含まれていたらtrue
   */
  private static boolean matchBlockMetaNames(Set<String> set, String uid, String uidMeta) {
    return set.contains(uid) || set.contains(uidMeta);
  }

  /**
   * 鉱石辞書名リスト内の要素と破壊対象ブロック名集合の要素で一致するものがあるかどうか
   *
   * @param set      破壊対象ブロック名集合
   * @param oreNames 鉱石辞書名リスト
   * @return 一致する要素があるならtrue
   */
  @Deprecated
  public static boolean matchOreNames(Set<String> set, List<String> oreNames) {
    return matchTagNames(set, oreNames);
  }

  /**
   * タグリスト内の要素と破壊対象ブロック名集合の要素で一致するものがあるかどうか
   *
   * @param set      破壊対象ブロック名集合
   * @param tagNames タグリスト
   * @return 一致する要素があるならtrue
   */
  public static boolean matchTagNames(Set<String> set, List<String> tagNames) {
    return tagNames.stream().anyMatch(set::contains);
  }

  /**
   * 破壊対象ブロック名集合内に引数のIBlockStateが表すブロックが含まれるかどうか
   *
   * @param set   破壊対象ブロック名集合
   * @param state 破壊対象判定IBlockState
   * @return 含まれていたらtrue
   */
  @SuppressWarnings("unused")
  public static boolean match(Set<String> set, BlockState state) {
    var key = ForgeRegistries.BLOCKS.getKey(state.getBlock());
    var uidStr = getUniqueString(key);
    var uidMetaStr = state.toString();
    var tagNames = makeStringDataFromBlockState(state);
    return matchTagNames(set, tagNames) || matchBlockMetaNames(set, uidStr, uidMetaStr);
  }
}
