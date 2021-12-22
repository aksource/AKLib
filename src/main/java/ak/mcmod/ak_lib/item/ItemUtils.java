package ak.mcmod.ak_lib.item;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Created by A.K. on 2021/09/14.
 */
@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class ItemUtils {
  private ItemUtils() {}


  /**
   * 手持ちアイテム破壊処理
   *
   * @param player プレイヤー
   * @param item   手持ちアイテム
   */
  public static void destroyItem(Player player, ItemStack item) {
    net.minecraftforge.event.ForgeEventFactory
            .onPlayerDestroyItem(player, item, InteractionHand.MAIN_HAND);
    player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
  }
}
