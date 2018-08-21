import java.util.Random;
public class Herbivore extends Animal {

    final static int MAX_AGE_HERBIVORE = 20;

    @Override
    public void update(Organism[] neighbours)
    {
        super.update(neighbours);
        setDirection(getNextMove(neighbours));
    }

    @Override
    public boolean isAlive()
    {
        if (getAge() > MAX_AGE_HERBIVORE || getEnergy() <= 0)
        {
            return false;
        }
        return true;
    }

    protected int getNextMove(Organism[] neighbours)
    {
        if (isHungry())
        {
            for (int i = 0; i < neighbours.length; i++)
            {
                if (neighbours[i] instanceof Plant)
                {
                    gainEnergy(4);
                    return i;
                }
            }
        }
        int[] possibleMoves = new int[5];
        int pos = 0;
        for (int i = 0; i < neighbours.length; i++)
        {
            if (neighbours[i] == null)
            {
                possibleMoves[pos] = i;
                pos++;
            }
        }
        if (pos != 0)
        {
            possibleMoves[pos] = -1;
            Random r = new Random();
            return possibleMoves[r.nextInt(pos)];
        }
        return -1;
    }

    @Override
    public String toString()
    {
        return "H ";
    }
}
