package net.aqusound.lilguysmod.item.custom;

import net.aqusound.lilguysmod.entity.custom.FireflyEntity;
import net.aqusound.lilguysmod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;

import static net.aqusound.lilguysmod.entity.ModEntities.Firefly;

public class bottleoffireflyitem extends Item {

    public bottleoffireflyitem(Properties pProperties) {

        super(pProperties);


        pProperties.stacksTo(64);

    }


    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {

        if (!context.getLevel().isClientSide()) {

            System.out.println(context.getItemInHand());

            BlockPos pos = context.getClickedPos();

            // Create your entity here

            if (context.getItemInHand().getItem() == ModItems.BOTTLE_OF_FIREFLY.get()) {

                //System.out.println(context.getItemInHand().getHoverName());


                FireflyEntity entity = new FireflyEntity(Firefly.get(), context.getLevel());

                if (context.getItemInHand().hasCustomHoverName()) {

                    entity.setCustomName(context.getItemInHand().getHoverName());

                }

                System.out.println(context.getLevel().addFreshEntity(entity));

                entity.moveTo(pos.above(), 0, 0);

                context.getItemInHand().shrink(1);

                context.getPlayer().addItem(Items.GLASS_BOTTLE.getDefaultInstance());


            }
        }

        return InteractionResult.SUCCESS;

    }
}
