package net.aqusound.lilguysmod.item.custom;

import net.aqusound.lilguysmod.LilGuysMod;
import net.aqusound.lilguysmod.entity.ModEntities;
import net.aqusound.lilguysmod.entity.custom.BottleOfWormThrowableEntity;
import net.aqusound.lilguysmod.entity.custom.WormEntity;
import net.aqusound.lilguysmod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import static net.aqusound.lilguysmod.entity.ModEntities.Worm;
import static net.aqusound.lilguysmod.item.ModItems.BOTTLE_OF_WORM;

public class bottleofwormitem extends Item {


    public bottleofwormitem(Properties pProperties) {

        super(pProperties);


        pProperties.stacksTo(64);

    }


/* @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {

            if (!context.getLevel().isClientSide()) {

                System.out.println(context.getItemInHand());

                BlockPos pos = context.getClickedPos();

                // Create your entity here

                if (context.getItemInHand().getItem() == ModItems.BOTTLE_OF_WORM.get()) {

                    //System.out.println(context.getItemInHand().getHoverName());


                    WormEntity entity = new WormEntity(Worm.get(), context.getLevel());

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

        }*/


    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {

        ItemStack itemstack = pPlayer.getItemInHand(pHand);
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                    SoundEvents.SPLASH_POTION_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!pLevel.isClientSide) {
                BottleOfWormThrowableEntity bottleOfWormEntity = new BottleOfWormThrowableEntity(pLevel, pPlayer);
                if (itemstack.hasCustomHoverName()) {
                    bottleOfWormEntity.setCustomName(itemstack.getHoverName());
                }
                bottleOfWormEntity.setItem(itemstack);
                bottleOfWormEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), -20.0F, 0.5F, 1.0F);
                pLevel.addFreshEntity(bottleOfWormEntity);
            }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());

    }

}
