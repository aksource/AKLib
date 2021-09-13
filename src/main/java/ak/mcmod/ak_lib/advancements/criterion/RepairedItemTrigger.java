package ak.mcmod.ak_lib.advancements.criterion;

import ak.mcmod.ak_lib.AkLib;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by A.K. on 2021/09/02.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RepairedItemTrigger implements ICriterionTrigger<RepairedItemTrigger.Instance> {
  public static final ResourceLocation ID = new ResourceLocation(AkLib.MOD_ID, "repaired_item");
  private final Map<PlayerAdvancements, RepairedItemTrigger.Listeners> listeners = Maps.newHashMap();

  @Override
  public ResourceLocation getId() {
    return ID;
  }

  @Override
  public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<Instance> listener) {
    RepairedItemTrigger.Listeners listeners = this.listeners.get(playerAdvancementsIn);

    if (listeners == null)
    {
      listeners = new RepairedItemTrigger.Listeners(playerAdvancementsIn);
      this.listeners.put(playerAdvancementsIn, listeners);
    }

    listeners.add(listener);
  }

  @Override
  public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<Instance> listener) {
    RepairedItemTrigger.Listeners listeners = this.listeners.get(playerAdvancementsIn);

    if (listeners != null)
    {
      listeners.remove(listener);

      if (listeners.isEmpty())
      {
        this.listeners.remove(playerAdvancementsIn);
      }
    }
  }

  @Override
  public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
    this.listeners.remove(playerAdvancementsIn);
  }

  @Override
  public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
    ItemPredicate itemLeft = ItemPredicate.deserialize(json.get("item_left"));
    ItemPredicate itemRight = ItemPredicate.deserialize(json.get("item_right"));
    ItemPredicate itemOutput = ItemPredicate.deserialize(json.get("item_output"));
    return new RepairedItemTrigger.Instance(itemLeft, itemRight, itemOutput);
  }

  public void trigger(EntityPlayerMP player, ItemStack itemLest, ItemStack itemRight, ItemStack itemOutput) {
    RepairedItemTrigger.Listeners listeners = this.listeners.get(player.getAdvancements());

    if (listeners != null)
    {
      listeners.trigger(itemLest, itemRight, itemOutput);
    }
  }

  public static class Instance extends AbstractCriterionInstance {
    private final ItemPredicate itemLeft;
    private final ItemPredicate itemRight;
    private final ItemPredicate itemOutput;

    public Instance(ItemPredicate itemLeft, ItemPredicate itemRight, ItemPredicate itemOutput) {
      super(RepairedItemTrigger.ID);
      this.itemLeft = itemLeft;
      this.itemRight = itemRight;
      this.itemOutput = itemOutput;
    }

    public boolean test(ItemStack itemLeft, ItemStack itemRight, ItemStack itemOutput) {
      return this.itemLeft.test(itemLeft) && this.itemRight.test(itemRight) && this.itemOutput.test(itemOutput);
    }
  }

  static class Listeners
  {
    private final PlayerAdvancements playerAdvancements;
    private final Set<Listener<RepairedItemTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<RepairedItemTrigger.Instance>>newHashSet();

    public Listeners(PlayerAdvancements playerAdvancementsIn)
    {
      this.playerAdvancements = playerAdvancementsIn;
    }

    public boolean isEmpty()
    {
      return this.listeners.isEmpty();
    }

    public void add(ICriterionTrigger.Listener<RepairedItemTrigger.Instance> listener)
    {
      this.listeners.add(listener);
    }

    public void remove(ICriterionTrigger.Listener<RepairedItemTrigger.Instance> listener)
    {
      this.listeners.remove(listener);
    }

    public void trigger(ItemStack itemLest, ItemStack itemRight, ItemStack itemOutput)
    {
      List<Listener<RepairedItemTrigger.Instance>> list = null;

      for (ICriterionTrigger.Listener<RepairedItemTrigger.Instance> listener : this.listeners)
      {
        if (((RepairedItemTrigger.Instance)listener.getCriterionInstance()).test(itemLest, itemRight, itemOutput))
        {
          if (list == null)
          {
            list = Lists.<ICriterionTrigger.Listener<RepairedItemTrigger.Instance>>newArrayList();
          }

          list.add(listener);
        }
      }

      if (list != null)
      {
        for (ICriterionTrigger.Listener<RepairedItemTrigger.Instance> listener1 : list)
        {
          listener1.grantCriterion(this.playerAdvancements);
        }
      }
    }
  }
}
