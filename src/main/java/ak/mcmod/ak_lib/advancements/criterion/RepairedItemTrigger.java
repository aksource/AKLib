package ak.mcmod.ak_lib.advancements.criterion;

import ak.mcmod.ak_lib.AkLib;
import com.google.gson.JsonObject;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Created by A.K. on 2021/09/02.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RepairedItemTrigger extends SimpleCriterionTrigger<RepairedItemTrigger.Instance> {

  public static final ResourceLocation ID = new ResourceLocation(AkLib.MOD_ID, "repaired_item");

  @Override
  protected Instance createInstance(JsonObject json, EntityPredicate.Composite entityPredicate, DeserializationContext deserializationContext) {
    ItemPredicate itemLeft = ItemPredicate.fromJson(json.get("item_left"));
    ItemPredicate itemRight = ItemPredicate.fromJson(json.get("item_right"));
    ItemPredicate itemOutput = ItemPredicate.fromJson(json.get("item_output"));
    return new RepairedItemTrigger.Instance(entityPredicate, itemLeft, itemRight, itemOutput);
  }

  @Override
  public ResourceLocation getId() {
    return ID;
  }

  public void trigger(ServerPlayer player, ItemStack itemLest, ItemStack itemRight, ItemStack itemOutput) {
    this.trigger(player, (instance) -> instance.test(itemLest, itemRight, itemOutput));
  }

  public static class Instance extends AbstractCriterionTriggerInstance {
    private final ItemPredicate itemLeft;
    private final ItemPredicate itemRight;
    private final ItemPredicate itemOutput;

    public Instance(EntityPredicate.Composite player, ItemPredicate itemLeft, ItemPredicate itemRight, ItemPredicate itemOutput) {
      super(RepairedItemTrigger.ID, player);
      this.itemLeft = itemLeft;
      this.itemRight = itemRight;
      this.itemOutput = itemOutput;
    }

    public boolean test(ItemStack itemLeft, ItemStack itemRight, ItemStack itemOutput) {
      return this.itemLeft.matches(itemLeft) && this.itemRight.matches(itemRight) && this.itemOutput.matches(itemOutput);
    }

    @Override
    public JsonObject serializeToJson(SerializationContext conditions) {
      var jsonObject = super.serializeToJson(conditions);
      jsonObject.add("item_left", this.itemLeft.serializeToJson());
      jsonObject.add("item_right", this.itemLeft.serializeToJson());
      jsonObject.add("item_output", this.itemLeft.serializeToJson());
      return jsonObject;
    }
  }
}
