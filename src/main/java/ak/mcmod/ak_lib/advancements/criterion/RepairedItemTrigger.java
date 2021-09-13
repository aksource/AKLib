package ak.mcmod.ak_lib.advancements.criterion;

import com.google.gson.JsonObject;
import ak.mcmod.ak_lib.AkLib;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Created by A.K. on 2021/09/02.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RepairedItemTrigger extends AbstractCriterionTrigger<RepairedItemTrigger.Instance> {
  public static final ResourceLocation ID = new ResourceLocation(AkLib.MOD_ID, "repaired_item");

  @Override
  protected Instance deserializeTrigger(JsonObject json, EntityPredicate.AndPredicate entityPredicate, ConditionArrayParser conditionsParser) {
    ItemPredicate itemLeft = ItemPredicate.deserialize(json.get("item_left"));
    ItemPredicate itemRight = ItemPredicate.deserialize(json.get("item_right"));
    ItemPredicate itemOutput = ItemPredicate.deserialize(json.get("item_output"));
    return new RepairedItemTrigger.Instance(entityPredicate, itemLeft, itemRight, itemOutput);
  }

  @Override
  public ResourceLocation getId() {
    return ID;
  }

  public void trigger(ServerPlayerEntity player, ItemStack itemLest, ItemStack itemRight, ItemStack itemOutput) {
    this.triggerListeners(player, (instance) -> instance.test(itemLest, itemRight, itemOutput));
  }

  public static class Instance extends CriterionInstance {
    private final ItemPredicate itemLeft;
    private final ItemPredicate itemRight;
    private final ItemPredicate itemOutput;

    public Instance(EntityPredicate.AndPredicate player, ItemPredicate itemLeft, ItemPredicate itemRight, ItemPredicate itemOutput) {
      super(RepairedItemTrigger.ID, player);
      this.itemLeft = itemLeft;
      this.itemRight = itemRight;
      this.itemOutput = itemOutput;
    }

    public boolean test(ItemStack itemLeft, ItemStack itemRight, ItemStack itemOutput) {
      return this.itemLeft.test(itemLeft) && this.itemRight.test(itemRight) && this.itemOutput.test(itemOutput);
    }

    @Override
    public JsonObject serialize(ConditionArraySerializer conditions) {
      JsonObject jsonobject = super.serialize(conditions);
      jsonobject.add("item_left", this.itemLeft.serialize());
      jsonobject.add("item_right", this.itemLeft.serialize());
      jsonobject.add("item_output", this.itemLeft.serialize());
      return jsonobject;
    }
  }
}
