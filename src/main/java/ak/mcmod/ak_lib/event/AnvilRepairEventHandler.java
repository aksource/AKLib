package ak.mcmod.ak_lib.event;

import ak.mcmod.ak_lib.advancements.criterion.RepairedItemTrigger;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

/**
 * Created by A.K. on 2021/09/01.
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
//@Mod.EventBusSubscriber(modid = AkLib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AnvilRepairEventHandler {

  @SubscribeEvent
  public void onAnvilRepairEvent(AnvilRepairEvent event) {
    if (event.getEntity() instanceof ServerPlayer) {
      CriterionTrigger<RepairedItemTrigger.Instance> criterionTrigger = CriteriaTriggers.getCriterion(RepairedItemTrigger.ID);
      if (Objects.nonNull(criterionTrigger) && criterionTrigger instanceof RepairedItemTrigger trigger) {
        trigger.trigger((ServerPlayer) event.getEntity(), event.getLeft(), event.getRight(), event.getOutput());
      }
    }
  }
}
