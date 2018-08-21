public class Plant extends Organism {

    final static int MAX_AGE_PLANT = 8;

    @Override
    public void update(Organism[] neighbours)
    {
        age();
    }

    @Override
    public boolean isAlive()
    {
        if (getAge() > MAX_AGE_PLANT)
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "P ";
    }

}
