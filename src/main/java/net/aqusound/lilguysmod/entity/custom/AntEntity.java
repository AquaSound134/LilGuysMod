package net.aqusound.lilguysmod.entity.custom;

import net.aqusound.lilguysmod.entity.ModEntities;
import net.aqusound.lilguysmod.entity.client.Little_Guy_Ant;
import net.minecraft.client.renderer.entity.layers.FoxHeldItemLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.HarvestFarmland;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class AntEntity  extends Animal{
    static final Predicate<ItemEntity> ANT_ITEMS = (p_289441_) -> {
        ItemStack itemstack = p_289441_.getItem();
        return (itemstack.is(Items.WHEAT) || itemstack.is(Items.SUGAR) || itemstack.is(Items.CARROT) || itemstack.is(Items.POTATO) && p_289441_.isAlive() && !p_289441_.hasPickUpDelay());
    };
    public AntEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        if (!this.isBaby()) {
            this.setCanPickUpLoot(true);
        }

    }

    public boolean canTakeItem(ItemStack pItemstack) {
       // System.out.println("TAKE");
        EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(pItemstack);
        if (!this.getItemBySlot(equipmentslot).isEmpty()) {
            return false;
        } else {
            return equipmentslot == EquipmentSlot.MAINHAND && super.canTakeItem(pItemstack);
        }
    }

    public float randomDrop = this.random.nextInt(2400) + 2400;
    public int waitToPickUp = 0;


    public void aiStep() {
        super.aiStep();
        if (!this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && --this.randomDrop <= 0) {

            System.out.println("Drop");
            dropEquipment();
            randomDrop = this.random.nextInt(2400) + 2400;
            waitToPickUp = this.random.nextInt(1800) + 1800;

        }

        //if farmland with wheat on it is close AND you aren't holding things, go break it and pick it up
        if (this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()){
            if (getFeetBlockState().is(Blocks.FARMLAND)) {
                Block block = getFeetBlockState().getBlock();
                BlockState blockState = getFeetBlockState();
                Block block1 = level().getBlockState(blockPosition().above()).getBlock();
                if (!(block1 == Blocks.AIR)) {
                    BlockState blockState1 = level().getBlockState(blockPosition().above());
                    //System.out.println(((CropBlock)block1).getAge(blockState1));
                    if (((CropBlock) block1).getAge(blockState1) == 7) {
                        System.out.println("Destroy");
                        level().destroyBlock(blockPosition().above(), true, this);
                    }
                }

            }
    }

    }




   /* static class AntToFarmGoal extends MoveToBlockGoal {
        private static final int GIVE_UP_TICKS = 1500;
        private final AntEntity antEntity;


        AntToFarmGoal(AntEntity antEntity, double pSpeedModifier) {

            super(antEntity, antEntity.isBaby() ? 2.0D : pSpeedModifier, 18);
            this.antEntity = antEntity;
            //!this.antEntity.getFeetBlockState().is(Blocks.FARMLAND) && !blockPos.closerToCenterThan(this.mob.position(), this.acceptedDistance()))
        }

        @Override
        public boolean canContinueToUse() {

            LevelReader pLevel = this.antEntity.level();

            if(this.antEntity.getFeetBlockState().is(pLevel.getBlockState(blockPos.above()).getBlock())){
                System.out.println(this.antEntity.getFeetBlockState().is(pLevel.getBlockState(blockPos.above()).getBlock()));
                return false;
            }
            return super.canContinueToUse();
        }

        @Override
        public boolean canUse() {
            return super.canUse();
        }


        public boolean shouldRecalculatePath() {
            return this.tryTicks % 160 == 0;
        }

        @Override
        protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
            //System.out.println("goal");
            BlockState CropBlock = pLevel.getBlockState(pPos.above());
            //System.out.println(pLevel.getBlockState(pPos).getBlock());
            if(pLevel.getBlockState(pPos).is(Blocks.FARMLAND)){
                System.out.println("Farm");
                return pLevel.getBlockState(pPos).is(Blocks.FARMLAND);
                //if(CropBlock.getBlock() != Blocks.AIR) {
                   // System.out.println(pLevel.getBlockState(pPos.above()).getBlock());
                    //return pLevel.getBlockState(pPos.above()).is(CropBlock.getBlock());
               // }
            }
            return false;
        }


    }

    */

    @Override
    protected void pickUpItem(ItemEntity pItemEntity) {

        if (this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && ANT_ITEMS.test(pItemEntity) && --this.waitToPickUp <= 0) {

            System.out.println("PICK UP");
            this.onItemPickup(pItemEntity);
            ItemStack itemstack = pItemEntity.getItem();
            this.setItemSlot(EquipmentSlot.MAINHAND, itemstack);
            this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
            this.take(pItemEntity, itemstack.getCount());
            pItemEntity.discard();
        }


    }


    protected void dropAllDeathLoot(DamageSource pDamageSource) {
        super.dropAllDeathLoot(pDamageSource);
    }

    protected void dropEquipment() { // Forge: move extra drops to dropEquipment to allow them to be captured by LivingDropsEvent
        super.dropEquipment();
        ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!itemstack.isEmpty()) {
            this.spawnAtLocation(itemstack);
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }

    }



    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
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

        float f;

        if(this.getDeltaMovement().x != 0){
            f = Math.min(pPartialTick * 6F, 1f);

            this.walkAnimation.update(1, 0.2f);
        }else{
            this.walkAnimation.update(0,0.2f);
        }

    }
    

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0,new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 2D, Ingredient.of(Items.SUGAR), false));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.10D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.10D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 9f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
       // this.goalSelector.addGoal(0,new AntToFarmGoal(this, 2D) );



    }

    public static AttributeSupplier.Builder createAttributes(){

        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 5D)
                .add(Attributes.MOVEMENT_SPEED, 0.1)
                .add(Attributes.FOLLOW_RANGE, 16);
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 4.0F);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.Ant.get().create(serverLevel);
    }

    public boolean isFood(ItemStack pStack){
        return pStack.is(Items.SUGAR);
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.AXOLOTL_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.AXOLOTL_DEATH;
    }

    public static boolean canSpawn(EntityType<AntEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return Animal.checkAnimalSpawnRules(entityType, level, spawnType, position, random);
    }

}
