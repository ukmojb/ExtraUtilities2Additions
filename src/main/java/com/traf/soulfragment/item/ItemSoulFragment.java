package com.traf.soulfragment.item;

import com.traf.soulfragment.SoulFragmentMod;
import com.traf.soulfragment.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.UUID;

import static com.traf.soulfragment.item.ModItems.ITEMS;

public class ItemSoulFragment extends Item implements IHasModel {

    public static final UUID MAX_HEALTH_MODIFIER_ID = UUID.fromString("8f3c82ec-5b32-4b1b-8957-4f6fba3f89c9");

    public ItemSoulFragment() {
        setTranslationKey("soul_fragment");
        setRegistryName("soul_fragment");
        setCreativeTab(CreativeTabs.MISC);

        ITEMS.add(this);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        double maxHealth = playerIn.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue();

//        if (maxHealth >= 20) {
//            return new ActionResult<>(EnumActionResult.PASS, itemstack);
//        }

        if (!worldIn.isRemote) {
            IAttributeInstance maxHealthAttribute = playerIn.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            if (maxHealthAttribute.getModifier(MAX_HEALTH_MODIFIER_ID) != null) {
                double value = maxHealthAttribute.getModifier(MAX_HEALTH_MODIFIER_ID).getAmount();
                maxHealthAttribute.removeModifier(MAX_HEALTH_MODIFIER_ID);
                maxHealthAttribute.applyModifier(new AttributeModifier(MAX_HEALTH_MODIFIER_ID, "Health boost", 2 + value, 0));
            } else {
                maxHealthAttribute.removeModifier(MAX_HEALTH_MODIFIER_ID);
                maxHealthAttribute.applyModifier(new AttributeModifier(MAX_HEALTH_MODIFIER_ID, "Health boost", 2, 0));
            }
            itemstack.shrink(1);
        }
        return new ActionResult<>(EnumActionResult.PASS, itemstack);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {

    }



    @Override
    public void registerModels() {
        SoulFragmentMod.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
