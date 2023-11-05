import java.util.ArrayList;
import java.util.List;

public class Main {

    static class Machine {
        String name;
        int productivity;
        int reliability;
        double cost;
        double score;

        Machine(String name, int productivity, int reliability, double cost) {
            this.name = name;
            this.productivity = productivity;
            this.reliability = reliability;
            this.cost = cost;
        }
    }

    public static void main(String[] args) {
        List<Machine> machines = new ArrayList<>();
        machines.add(new Machine("Станок 1", 1000, 100, 10));
        machines.add(new Machine("Станок 2", 1100, 110, 9));
        machines.add(new Machine("Станок 3", 1200, 95, 9.5));
        machines.add(new Machine("Станок 4", 1300, 89, 8));
        machines.add(new Machine("Станок 5", 1400, 79, 8.4));
        machines.add(new Machine("Станок 6", 1650, 80, 9));
        machines.add(new Machine("Станок 7", 1700, 75, 7.8));
        machines.add(new Machine("Станок 8", 1850, 78, 6.5));
        machines.add(new Machine("Станок 9", 1900, 70, 7.5));
        machines.add(new Machine("Станок 10", 2000, 65, 7.1));


        double[] weights = {0.2, 0.4, 0.4};

        List<Machine> paretoOptimal = new ArrayList<>();

        List<String> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();

        double totalScore = 0;
        double minScore = Double.MAX_VALUE;
        double maxScore = Double.MIN_VALUE;

        for (Machine machine : machines) {
            double score = machine.productivity * weights[0] + machine.reliability * weights[1] + machine.cost * weights[2];
            totalScore += score;
            minScore = Math.min(minScore, score);
            maxScore = Math.max(maxScore, score);

            x.add(machine.name);
            y.add(score);

            boolean betterAlternative = false;
            for (Machine otherMachine : machines) {
                double otherScore = otherMachine.productivity * weights[0] + otherMachine.reliability * weights[1] + otherMachine.cost * weights[2];
                if (otherScore > score) {
                    betterAlternative = true;
                    break;
                }
            }

            machine.score = score;

            if (!betterAlternative) {
                paretoOptimal.add(machine);
            }
        }

        double averageScore = totalScore / machines.size();

        System.out.println("Повний список станків з обрахунками:");
        for (Machine machine : machines) {
            System.out.println(String.format("%s - Продуктивність: %d, Надійність: %d, Вартість: %.1f тис.грн., Оцінка: %.2f",
                    machine.name, machine.productivity, machine.reliability, machine.cost, machine.score));
        }

        System.out.println("\nСтанок з найкращою оцінкою:");
        paretoOptimal.sort((m1, m2) -> Double.compare(m2.score, m1.score)); // Сортування за оцінкою у спадаючому порядку
        for (int i = 0; i < Math.min(4, paretoOptimal.size()); i++) {
            Machine machine = paretoOptimal.get(i);
            System.out.println(String.format("%d. %s - Продуктивність: %d, Надійність: %d, Вартість: %.1f тис.грн., Оцінка: %.2f",
                    i + 1, machine.name, machine.productivity, machine.reliability, machine.cost, machine.score));
        }

        System.out.println("\nСередня оцінка: " + averageScore);
        System.out.println("Мінімальна оцінка: " + minScore);
        System.out.println("Максимальна оцінка: " + maxScore);
    }
}