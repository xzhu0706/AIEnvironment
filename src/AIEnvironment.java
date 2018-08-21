public class AIEnvironment {

    private Organism[][] grid;
    private Organism[][] newGrid;
    private int generation;

    public AIEnvironment(int row, int column) {
        grid = new Organism[row][column];
        newGrid = new Organism[row][column];
        generation = 0;
    }

    public void initializeGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = createOrganism();
            }
        }
    }

    private void resetnewGrid() {
        for (int i = 0; i < newGrid.length; i++) {
            for (int j = 0; j < newGrid[i].length; j++) {
                newGrid[i][j] = null;
            }
        }
    }

    private void copynewGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = newGrid[i][j];
            }
        }
    }

    private Organism createOrganism() {
        Organism organism = null;
        double num = Math.random();
        if (num < 0.3)
            organism = new Plant();
        else if (num < 0.6)
            organism = new Herbivore();
        else if (num < 0.65)
            organism = new Carnivore();
        return organism;
    }

    private Organism[] getNeighbours(int row, int col) {
        Organism[] neighbours = new Organism[4];
        int pos = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if ((i != row && j == col) || (i == row && j != col)) {
                    if ((i >= 0 && i < grid.length) && (j >= 0 && j < grid[i].length)) {
                        neighbours[pos] = grid[i][j];
                    } else {
                        neighbours[pos] = new Carnivore();  // pretend there is a carnivore so it cannot move there
                    }
                    pos++;
                }
            }
        }
        return neighbours;
    }

    private void moveAgent(int i, int j, String move, Organism agent) {
        if (move == "north") {
            newGrid[i - 1][j] = agent;
            grid[i - 1][j] = null;
        } else if (move == "west") {
            newGrid[i][j + 1] = agent;
            grid[i][j + 1] = null;
        } else if (move == "south") {
            newGrid[i + 1][j] = agent;
            grid[i + 1][j] = null;
        } else if (move == "east") {
            newGrid[i][j - 1] = agent;
            grid[i][j - 1] = null;
        }
        grid[i][j] = newGrid[i][j] = null;
    }

    private void giveBirth(int i, int j, String move, Organism agent) {
        Organism newborn;
        if (agent instanceof Carnivore) {
            newborn = new Carnivore();
        } else {
            newborn = new Herbivore();
        }
        if (move == "north") {
            newGrid[i - 1][j] = grid[i - 1][j] = newborn;
        } else if (move == "west") {
            newGrid[i][j + 1] = grid[i][j + 1] = newborn;
        } else if (move == "south") {
            newGrid[i + 1][j] = grid[i + 1][j] = newborn;
        } else if (move == "east") {
            newGrid[i][j - 1] = grid[i][j - 1] = newborn;
        }
        newGrid[i][j] = agent;
    }

    public void simulate() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    if (!(grid[i][j].isAlive())) {
                        newGrid[i][j] = null;
                        grid[i][j] = null;
                    } else {
                        if (grid[i][j] instanceof Carnivore) {
                            Organism[] neighbours = getNeighbours(i, j);
                            grid[i][j].update(neighbours);
                            Organism current = grid[i][j];
                            Animal a = (Animal) current;
                            String move = a.getMovement();
                            if (move != null) {
                                if (a.isReproducable()) {
                                    giveBirth(i, j, move, current);
                                    a.loseEnergy(5);
                                } else {
                                    moveAgent(i, j, move, current);
                                }
                                a.setMovement(null);  // movement completed
                            } else {
                                newGrid[i][j] = current;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    if (!(grid[i][j].isAlive())) {
                        newGrid[i][j] = null;
                    } else {
                        Organism[] neighbours = getNeighbours(i, j);
                        if (grid[i][j] instanceof Herbivore) {
                            grid[i][j].update(neighbours);
                            Organism current = grid[i][j];
                            Animal a = (Animal) grid[i][j];
                            String move = a.getMovement();
                            if (move != null) {
                                if (a.isReproducable()) {
                                    giveBirth(i, j, move, current);
                                    a.loseEnergy(5);
                                } else {
                                    moveAgent(i, j, move, current);
                                }
                                a.setMovement(null); // movement completed
                            } else {
                                newGrid[i][j] = current;
                            }
                        } else if (grid[i][j] instanceof Plant) {
                            if (grid[i][j].isAlive()) {
                                grid[i][j].update(neighbours);
                                newGrid[i][j] = grid[i][j];
                            }
                        }
                    }
                }
            }
        }
        if (Math.random() > 0.5) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    {
                        if (newGrid[i][j] == null) {
                            if (Math.random() < 0.3 && Math.random() > 0.8) {
                                newGrid[i][j] = new Plant();
                            }
                        }
                    }
                }
            }
        }
        copynewGrid();
        resetnewGrid();
        generation++;
    }

    @Override
    public String toString() {
        String display = "Generation " + generation + "\n";
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    display += grid[i][j];
                } else {
                    display += "_ ";
                }
            }
            display += "\n";
        }
        return display;
    }
}
