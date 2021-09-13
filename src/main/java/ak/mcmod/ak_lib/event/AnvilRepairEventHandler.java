package ak.mcmod.ak_lib.event;

import ak.mcmod.ak_lib.advancements.criterion.RepairedItemTrigger;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

/**
 * Created by A.K. on 2021/09/01.
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class AnvilRepairEventHandler {

  @SubscribeEvent
  public void onAnvilRepairEvent(AnvilRepairEvent event) {
    if (event.getEntityPlayer() instanceof EntityPlayerMP) {
      ICriterionTrigger<RepairedItemTrigger.Instance> criterionTrigger = CriteriaTriggers.get(RepairedItemTrigger.ID);
      if (Objects.nonNull(criterionTrigger) && criterionTrigger instanceof RepairedItemTrigger) {
        RepairedItemTrigger trigger = (RepairedItemTrigger) criterionTrigger;
        trigger.trigger((EntityPlayerMP) event.getEntityPlayer(), event.getItemInput(), event.getIngredientInput(), event.getItemResult());
      }
    }
  }
}
