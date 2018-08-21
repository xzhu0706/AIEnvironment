public class AIEnvironmentTestDrive {

    public static void main(String[] args) {
        AIEnvironment environment = new AIEnvironment(30, 30);
        environment.initializeGrid();
        System.out.println(environment);
        for (int i = 0; i < 30; i++) {
            environment.simulate();
            System.out.println(environment);
        }
    }

}
