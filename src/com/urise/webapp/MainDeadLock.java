package com.urise.webapp;

public class MainDeadLock {
    private static Bill bill1 = new Bill(1000);
    private static Bill bill2 = new Bill(2000);

    public static void main(String[] args) {
        new Thread(() -> sendMoney(bill1, bill2, 500)).start();

        new Thread(() -> sendMoney(bill2, bill1, 400)).start();

    }

    public static void sendMoney(Bill from, Bill to, int sum) {
        synchronized (from) {
            boolean isPossible = bill2.check(sum);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (to) {
                if (isPossible) {
                    from.setSum(from.getSum() - sum);
                    from.setSum(to.getSum() + sum);
                } else {
                    System.out.println("Not enough money");
                }
            }
        }

    }


    private static class Bill {
        private int sum;

        public Bill(int sum) {
            this.sum = sum;
        }

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public boolean check(int sum) {
            return this.getSum() > sum;
        }
    }
}
