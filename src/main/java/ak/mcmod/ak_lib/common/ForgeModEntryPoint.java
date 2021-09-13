package ak.mcmod.ak_lib.common;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Forge Mod Entry Point Class
 * Created by A.K. on 2021/09/04.
 */
public abstract class ForgeModEntryPoint {

  /**
   * Registration : MinecraftForgeEvent, LifecycleEvent, Configuration files or Other Objects
   */
  public ForgeModEntryPoint() {
    final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    eventBus.addListener(this::setupCommon);
    eventBus.addListener(this::setupClient);
    eventBus.addListener(this::constructMod);
    eventBus.addListener(this::setupDedicatedServer);
    eventBus.addListener(this::enqueueInterMod);
    eventBus.addListener(this::processInterMod);
    this.setupConstructor(eventBus);
  }

  abstract protected void setupConstructor(final IEventBus eventBus);

  /**
   * Registration : Message, Capability
   *
   * @param event event
   */
  protected void setupCommon(final FMLCommonSetupEvent event) {
//    CriteriaTriggers.register();
  }

  /**
   * Registration : Something only for Client
   *
   * @param event event
   */
  protected void setupClient(final FMLClientSetupEvent event) {}

  protected void constructMod(final FMLConstructModEvent event) {}

  protected void setupDedicatedServer(final FMLDedicatedServerSetupEvent event) {}

  protected void enqueueInterMod(final InterModEnqueueEvent event) {}

  protected void processInterMod(final InterModProcessEvent event) {}
}
