public abstract class Animal extends Organism {

    public static int MAX_ENERGY = 20;
    private int energy;
    private String movement;

    public Animal()
    {
        super();
        energy = 15;
    }

    public String getMovement()
    {
        return movement;
    }

    public void setMovement(String movement)
    {
        this.movement = movement;
    }

    @Override
    public void update(Organism[] neighbours)
    {
        age();
        loseEnergy(1);
    }

    public int getEnergy()
    {
        return energy;
    }

    public void gainEnergy(int energy)
    {
        this.energy += energy;
        if (energy > MAX_ENERGY)
        {
            this.energy = MAX_ENERGY;
        }
    }

    public void loseEnergy(int energy)
    {
        this.energy -= energy;
    }

    public boolean isHungry()
    {
        if (energy <= 16)
        {
            return true;
        }
        return false;
    }

    public boolean isReproducable()
    {
        if ((getAge() >= 5) && (getAge() <= 15) && (energy >= 10))
            return true;
        return false;
    }

    public void setDirection(int a)
    {
        if (a == -1)
            movement = null;
        if (a == 0)
            movement = "north";
        if (a == 1)
            movement = "east";
        if (a == 2)
            movement = "west";
        if (a == 3)
            movement = "south";
    }

    protected abstract int getNextMove(Organism[] neighbours);
}
