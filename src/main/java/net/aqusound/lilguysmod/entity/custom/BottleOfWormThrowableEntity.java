package net.aqusound.lilguysmod.entity.custom;

import net.aqusound.lilguysmod.entity.ModEntities;
import net.aqusound.lilguysmod.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import static net.aqusound.lilguysmod.entity.ModEntities.Worm;

public class BottleOfWormThrowableEntity extends ThrowableItemProjectile {
    public BottleOfWormThrowableEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BottleOfWormThrowableEntity(Level plevel){
        super(ModEntities.BottleOfWormEntity.get(), plevel);

    }


    public BottleOfWormThrowableEntity(Level plevel, LivingEntity pEntity){
        super(ModEntities.BottleOfWormEntity.get(),pEntity, plevel);


    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.BOTTLE_OF_WORM.get();
    }


    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        //super.onHitBlock(pResult);
        if(!this.level().isClientSide){
            this.level().broadcastEntityEvent(this, (byte)3);
            WormEntity worm = new WormEntity(Worm.get(), this.level());
            EntityType worm2 = Worm.get();
            if(this.hasCustomName()) {
                worm.setCustomName(this.getCustomName());
            }
            playSound(SoundEvents.SPLASH_POTION_BREAK);

            this.level().addFreshEntity(worm);
            worm.moveTo(blockPosition().above(), 0, 0);

        }

        this.discard();

        this.remove(RemovalReason.DISCARDED);

        super.onHitBlock(pResult);
    }
}
