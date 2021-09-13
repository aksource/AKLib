package ak.mcmod.ak_lib;

import ak.mcmod.ak_lib.advancements.criterion.RepairedItemTrigger;
import ak.mcmod.ak_lib.event.AnvilRepairEventHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by A.K. on 2021/09/04.
 */
@Mod(modid = AkLib.MOD_ID,
        name = AkLib.MOD_NAME,
        version = AkLib.MOD_VERSION,
        dependencies = AkLib.MOD_DEPENDENCIES,
        useMetadata = true,
        acceptedMinecraftVersions = AkLib.MOD_MC_VERSION)
public class AkLib {
  public static final String MOD_ID = "ak-lib";
  public static final String MOD_NAME = "AKLib";
  public static final String MOD_VERSION = "@VERSION@";
  public static final String MOD_DEPENDENCIES = "required-after:forge@[14.23,)";
  public static final String MOD_MC_VERSION = "[1.12,1.12.2]";

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {

  }
  @Mod.EventHandler
  public void load(FMLInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(new AnvilRepairEventHandler());
    CriteriaTriggers.register(new RepairedItemTrigger());
  }
}
