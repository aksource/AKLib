package ak.mcmod.ak_lib.item;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Created by A.K. on 2021/09/14.
 */
@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class ItemUtils {
  private ItemUtils(){}


  /**
   * 手持ちアイテム破壊処理
   *
   * @param player プレイヤー
   * @param item   手持ちアイテム
   */
  public static void destroyItem(PlayerEntity player, ItemStack item) {
    net.minecraftforge.event.ForgeEventFactory
            .onPlayerDestroyItem(player, item, Hand.MAIN_HAND);
    player.setItemInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
  }
}
