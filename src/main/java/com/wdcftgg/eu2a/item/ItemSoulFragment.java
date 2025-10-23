package com.wdcftgg.eu2a.item;

import com.wdcftgg.eu2a.ExtraUtilities2Additions;
import com.wdcftgg.eu2a.mods.unstabletools.UnstableTools;
import com.wdcftgg.eu2a.util.IHasModel;
import com.wdcftgg.eu2a.util.Tools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

import static com.wdcftgg.eu2a.item.ModItems.ITEMS;

public class ItemSoulFragment extends Item implements IHasModel {

    public static final UUID MAX_HEALTH_MODIFIER_ID = UUID.fromString("8f3c82ec-5b32-4b1b-8957-4f6fba3f89c9");

    public ItemSoulFragment() {
        setTranslationKey("soul_fragment");
        setRegistryName("soul_fragment");
        this.setCreativeTab(ExtraUtilities2Additions.creativeTab);

        ITEMS.add(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (!stack.hasTagCompound()) return;
        boolean stable = false;
        if (stack.getTagCompound() != null) {
            stable = stack.getTagCompound().getBoolean("fragile");
        }

        if (stable) {
            tooltip.add(I18n.format(UnstableTools.MODID + ".soul_fragment.fragile"));
        }

    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return (stack.hasTagCompound() && (stack.getTagCompound().getBoolean("fragile")));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        double maxHealth = playerIn.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue();

//        if (maxHealth >= 20) {
//            return new ActionResult<>(EnumActionResult.PASS, itemstack);
//        }

        IAttributeInstance maxHealthAttribute = playerIn.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        AttributeModifier addmodifier;
        if (maxHealthAttribute.getModifier(MAX_HEALTH_MODIFIER_ID) != null) {
            double value = maxHealthAttribute.getModifier(MAX_HEALTH_MODIFIER_ID).getAmount();
            maxHealthAttribute.removeModifier(MAX_HEALTH_MODIFIER_ID);
            addmodifier = new AttributeModifier(MAX_HEALTH_MODIFIER_ID, "Health boost", 2 + value, 0);
            maxHealthAttribute.applyModifier(addmodifier);
        } else {
            maxHealthAttribute.removeModifier(MAX_HEALTH_MODIFIER_ID);
            addmodifier = new AttributeModifier(MAX_HEALTH_MODIFIER_ID, "Health boost", 2, 0);
            maxHealthAttribute.applyModifier(addmodifier);
        }

        Tools.saveHealthNBT(playerIn, addmodifier.getAmount(), MAX_HEALTH_MODIFIER_ID);

        if (!worldIn.isRemote) {
            itemstack.shrink(1);
        }
        return new ActionResult<>(EnumActionResult.PASS, itemstack);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {

    }



    @Override
    public void registerModels() {
        ExtraUtilities2Additions.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
