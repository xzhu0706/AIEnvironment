public abstract class Organism {

    private int age;
    private boolean alive;

    public Organism()
    {
        age = 0;
        alive = true;
    }

    public int getAge()
    {
        return age;
    }

    public void age()
    {
        age++;
    }

    public abstract void update(Organism[] neighbours);
    public abstract boolean isAlive();

}
