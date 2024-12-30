package net.aqusound.lilguysmod.entity.custom;

import net.aqusound.lilguysmod.entity.ModEntities;
import net.aqusound.lilguysmod.entity.client.Lil_Guy_Lizard;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.stream.Stream;

public class LizardEntity extends TamableAnimal {
    public LizardEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setTame(false);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState tailAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {



            if (this.idleAnimationTimeout <= 0) {
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

           // this.tailAnimationState.stop();

            this.walkAnimation.update(1, 0.2f);
        }else{

            this.walkAnimation.update(0,0.2f);


        }
        if(this.getPose() == Pose.SITTING){
            this.idleAnimationState.stop();
            this.tailAnimationState.startIfStopped(this.tickCount);

        }else{

            this.tailAnimationState.stop();
            this.idleAnimationState.startIfStopped(this.tickCount);
        }

    }





    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0,new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 3D, Ingredient.of(Items.EGG), false));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.5D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.10D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 9f));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 3.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

    }


    public static AttributeSupplier.Builder createAttributes(){

        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 7D)
                .add(Attributes.MOVEMENT_SPEED, 0.1f)
                .add(Attributes.FOLLOW_RANGE, 12);

    }


    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Item item = itemstack.getItem();
        if (this.level().isClientSide) {
            boolean flag = this.isOwnedBy(pPlayer) || this.isTame() || itemstack.is(Items.EGG) && !this.isTame();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else if (this.isTame()) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                this.heal((float)itemstack.getFoodProperties(this).getNutrition());
                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            } else {
                if (item instanceof DyeItem) {
                    DyeItem dyeitem = (DyeItem)item;
                    if (this.isOwnedBy(pPlayer)) {


                        return super.mobInteract(pPlayer, pHand);
                    }
                }

                InteractionResult interactionresult = super.mobInteract(pPlayer, pHand);
                if ((!interactionresult.consumesAction() || this.isBaby()) && this.isOwnedBy(pPlayer)) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    if(isOrderedToSit()){
                        this.setPose(Pose.SITTING);
                        System.out.println("Sitting");
                    }else{
                        this.setPose(Pose.STANDING);
                        System.out.println("stand");
                    }
                    this.setTarget((LivingEntity)null);
                    return InteractionResult.SUCCESS;
                } else {
                    return interactionresult;
                }
            }
        } else if (itemstack.is(Items.EGG)) {
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, pPlayer)) {
                this.tame(pPlayer);
                this.navigation.stop();
                this.setTarget((LivingEntity)null);
                this.setOrderedToSit(true);
                this.level().broadcastEntityEvent(this, (byte)7);
            } else {
                this.level().broadcastEntityEvent(this, (byte)6);
            }

            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

    public void setTame(boolean pTamed) {
        super.setTame(pTamed);
        if (pTamed) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(7.0D);
        }
    }


    public boolean canBeLeashed(Player pPlayer) {
        return super.canBeLeashed(pPlayer);
    }


    @Override
    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(SoundEvents.WOLF_STEP, 0.15F, 4.0F);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        //return ModEntities.Lizard.get().create(serverLevel);
        LizardEntity lizard = ModEntities.Lizard.get().create(serverLevel);
        if (lizard != null) {
            UUID uuid = this.getOwnerUUID();
            if (uuid != null) {
                lizard.setOwnerUUID(uuid);
                lizard.setTame(true);
            }
        }
        return lizard;
    }

    public boolean isFood(ItemStack pStack){
        return pStack.is(Items.EGG);
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.FOX_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.FOX_DEATH;
    }

    public static boolean canSpawn(EntityType<LizardEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return Animal.checkAnimalSpawnRules(entityType, level, spawnType, position, random);
    }
}
