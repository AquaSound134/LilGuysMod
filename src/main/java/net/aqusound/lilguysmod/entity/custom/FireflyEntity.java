package net.aqusound.lilguysmod.entity.custom;

import net.aqusound.lilguysmod.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
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
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import static net.aqusound.lilguysmod.item.ModItems.BOTTLE_OF_FIREFLY;
import static net.aqusound.lilguysmod.item.ModItems.BOTTLE_OF_WORM;

public class FireflyEntity extends Animal {
    public FireflyEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
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

    public void aiStep() {
        super.aiStep();

        BlockPos pPos = getOnPos().above();




        BlockPos a = getOnPos().east(1).above();
        BlockPos b = getOnPos().north(1).above();
        BlockPos c = getOnPos().west(1).above();
        BlockPos d = getOnPos().south(1).above();

        BlockPos ne = getOnPos().north(1).east(1).above();
        BlockPos nw = getOnPos().north(1).west(1).above();
        BlockPos se = getOnPos().south(1).east(1).above();
        BlockPos sw = getOnPos().south(1).west(1).above();



        if( level().getBlockState(a).getBlock() == Blocks.LIGHT){

          //  System.out.println("Light east");
            level().setBlock(a, Blocks.AIR.defaultBlockState(),1);

        }
        if( level().getBlockState(b).getBlock() == Blocks.LIGHT){

           // System.out.println("Light north");
            level().setBlock(b, Blocks.AIR.defaultBlockState(),1);

        }
        if( level().getBlockState(c).getBlock() == Blocks.LIGHT){

           // System.out.println("Light west");
            level().setBlock(c, Blocks.AIR.defaultBlockState(),1);

        }
        if( level().getBlockState(d).getBlock() == Blocks.LIGHT){

           // System.out.println("Light south");
            level().setBlock(d, Blocks.AIR.defaultBlockState(),1);

        }
        if( level().getBlockState(ne).getBlock() == Blocks.LIGHT){

          //  System.out.println("Light ne");
            level().setBlock(ne, Blocks.AIR.defaultBlockState(),1);

        }
        if( level().getBlockState(nw).getBlock() == Blocks.LIGHT){

           // System.out.println("Light nw");
            level().setBlock(nw, Blocks.AIR.defaultBlockState(),1);

        }
        if( level().getBlockState(se).getBlock() == Blocks.LIGHT){

           // System.out.println("Light se");
            level().setBlock(se, Blocks.AIR.defaultBlockState(),1);

        }
        if( level().getBlockState(sw).getBlock() == Blocks.LIGHT){

           // System.out.println("Light sw");
            level().setBlock(sw, Blocks.AIR.defaultBlockState(),1);

        }

        int lvl = Blocks.LIGHT.getLightEmission(Blocks.LIGHT.defaultBlockState(), level(), pPos);
        lvl = 4;
        BlockState light = Blocks.LIGHT.defaultBlockState().setValue(BlockStateProperties.LEVEL, 7);

       level().setBlock(pPos, light,1);

        if(isDeadOrDying()){

            level().setBlock(pPos, Blocks.AIR.defaultBlockState(),1);

        }





    }

    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {

        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        Item item = itemstack.getItem();

        if (!this.level().isClientSide) { //STUPID EXCLAMATION POINT WHY DID I FORGET YOU
            if(itemstack.is(Items.GLASS_BOTTLE)) {

                level().setBlock(this.getOnPos().above(), Blocks.AIR.defaultBlockState(), 2);

                itemstack.shrink(1);

                //System.out.println(this.getCustomName());

                ItemStack item2 = new ItemStack(BOTTLE_OF_FIREFLY.get().getDefaultInstance().getItem());

                if(this.hasCustomName()) {
                    item2.setHoverName(this.getCustomName());
                }

                pPlayer.addItem(item2);



                this.remove(RemovalReason.KILLED);

               

            }

        }
        return super.mobInteract(pPlayer, pHand);
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





    }



    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0,new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 2D, Ingredient.of(Items.HONEY_BOTTLE), false));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.10D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.10D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 9f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        //this.goalSelector.addGoal(7, new Mov)

    }

    public static AttributeSupplier.Builder createAttributes(){

        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2D)
                .add(Attributes.MOVEMENT_SPEED, 0.1)
                .add(Attributes.FOLLOW_RANGE, 16);



    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.Firefly.get().create(serverLevel);
    }

    public boolean isFood(ItemStack pStack){
        return pStack.is(Items.HONEY_BOTTLE);
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.RABBIT_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.RABBIT_DEATH;
    }

    public static boolean canSpawn(EntityType<FireflyEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return Animal.checkAnimalSpawnRules(entityType, level, spawnType, position, random);
    }

}
