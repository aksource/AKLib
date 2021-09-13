package ak.mcmod.ak_lib.event;

import ak.mcmod.ak_lib.advancements.criterion.RepairedItemTrigger;
import ak.mcmod.ak_lib.AkLib;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

/**
 * Created by A.K. on 2021/09/01.
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@Mod.EventBusSubscriber(modid = AkLib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AnvilRepairEventHandler {

  @SubscribeEvent
  public void onAnvilRepairEvent(AnvilRepairEvent event) {
    if (event.getPlayer() instanceof ServerPlayerEntity) {
      ICriterionTrigger<RepairedItemTrigger.Instance> criterionTrigger = CriteriaTriggers.get(RepairedItemTrigger.ID);
      if (Objects.nonNull(criterionTrigger) && criterionTrigger instanceof RepairedItemTrigger) {
        RepairedItemTrigger trigger = (RepairedItemTrigger) criterionTrigger;
        trigger.trigger((ServerPlayerEntity) event.getPlayer(), event.getItemInput(), event.getIngredientInput(), event.getItemResult());
      }
    }
  }
}
