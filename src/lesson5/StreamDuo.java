package lesson5;

/**
 * @autor Kunakbaev Artem
 */
class StreamDuo {
    private static final int size = 10000000;
    private static final int half = size / 2;
    private final float[] arr;
    private float[] a1;
    private float[] a2;

    StreamDuo() {
        arr = new float[size];
        a1 = new float[half];
        a2 = new float[half];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
    }

    void doOperation() throws InterruptedException {
        final long beginTime = System.currentTimeMillis();

        divideArr();

        final Thread steam1 = new Thread(() -> a1 = doHalfOperation(a1, 0));
        final Thread steam2 = new Thread(() -> a2 = doHalfOperation(a2, half));
        steam1.start();
        steam2.start();
        steam1.join();
        steam2.join();

        mergeArr();

        System.out.println(System.currentTimeMillis() - beginTime);

    }

    private void divideArr() {
        System.arraycopy(arr, 0, a1, 0, half);
        System.arraycopy(arr, half, a2, 0, half);
    }

    private float[] doHalfOperation(float[] array, int firstIndex) {
        for (int i = 0; i < half; i++, firstIndex++) {
            arr[i] = (float) (array[i]
                    * Math.sin(0.2f + firstIndex / 5)
                    * Math.cos(0.2f + firstIndex / 5)
                    * Math.cos(0.4f + firstIndex / 2));
        }
        return arr;
    }

    private void mergeArr() {
        System.arraycopy(a1, 0, arr, 0, half);
        System.arraycopy(a2, 0, arr, half, half);
    }
}
