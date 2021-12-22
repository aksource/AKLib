package ak.mcmod.ak_lib;

import ak.mcmod.ak_lib.advancements.criterion.RepairedItemTrigger;
import ak.mcmod.ak_lib.common.ForgeModEntryPoint;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Created by A.K. on 2021/09/04.
 */
@Mod(AkLib.MOD_ID)
public class AkLib extends ForgeModEntryPoint {
  public static final String MOD_ID = "ak_lib";

  @Override
  protected void setupConstructor(IEventBus eventBus) {
    MinecraftForge.EVENT_BUS.register(AnvilRepairEvent.class);
  }

  @Override
  protected void setupCommon(FMLCommonSetupEvent event) {
    super.setupCommon(event);
    CriteriaTriggers.register(new RepairedItemTrigger());
  }
}
