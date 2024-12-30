package net.aqusound.lilguysmod.entity.custom;

import net.aqusound.lilguysmod.LilGuysMod;
import net.aqusound.lilguysmod.entity.ModEntities;
import net.aqusound.lilguysmod.item.ModItems;
import net.aqusound.lilguysmod.item.custom.bottleofwormitem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import static net.aqusound.lilguysmod.item.ModItems.BOTTLE_OF_WORM;
import static net.aqusound.lilguysmod.item.ModItems.ITEMS;

public class WormEntity extends Animal {


    public int eggTime = this.random.nextInt(2400) + 2400;

    public WormEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }




    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.eggTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(Items.DIRT);
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.eggTime = this.random.nextInt(2400) + 2400;
        };
    }



    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;







    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }



    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        //System.out.println("Walk Update");
        float f;
        if(this.getPose() == Pose.STANDING){

            //System.out.println(pPartialTick);
           f = Math.min(pPartialTick * 6f, 1f);
           // System.out.println(f);


        }else{
            f = 0;
        }
        //System.out.println(f);
        if(f>0) {
            this.walkAnimation.update(1, 0.2f);
        }else{
            this.walkAnimation.update(0, 0.2f);
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0,new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.APPLE), false));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.10D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.10D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 9f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        //this.goalSelector.addGoal(7, new Mov)

    }

    public static AttributeSupplier.Builder createAttributes(){

        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 3D)
                .add(Attributes.MOVEMENT_SPEED, 0.07)
                .add(Attributes.FOLLOW_RANGE, 16);


    }


    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {

        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        
        Item item = itemstack.getItem();

        if (!this.level().isClientSide) { //STUPID EXCLAMATION POINT WHY DID I FORGET YOU
            if(itemstack.is(Items.GLASS_BOTTLE)) {

                itemstack.shrink(1);

                //System.out.println(this.getCustomName());

                ItemStack item2 = new ItemStack(BOTTLE_OF_WORM.get().getDefaultInstance().getItem());

                if(this.hasCustomName()) {
                    item2.setHoverName(this.getCustomName());
                }

                pPlayer.addItem(item2);

                this.remove(RemovalReason.KILLED);

            }

        }
        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.Worm.get().create(serverLevel);
    }

    public boolean isFood(ItemStack pStack){
        return pStack.is(Items.APPLE);
    }

    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(SoundEvents.STRIDER_STEP, 0.15F, 1.0F);
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SLIME_HURT_SMALL;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH;
    }


    public static boolean canSpawn(EntityType<WormEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return Animal.checkAnimalSpawnRules(entityType, level, spawnType, position, random);
    }

}
