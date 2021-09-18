package ak.mcmod.ak_lib.item;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

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
  public static void destroyItem(EntityPlayer player, ItemStack item) {
    net.minecraftforge.event.ForgeEventFactory
            .onPlayerDestroyItem(player, item, EnumHand.MAIN_HAND);
    player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
  }
}
