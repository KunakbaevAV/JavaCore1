package lesson5;

/**
 * @autor Kunakbaev Artem
 */
final class StreamMono {
    private static final int size = 10000000;
    private final float[] arr;

    StreamMono() {
        arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
    }

    void doOperation() {
        final long beginTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println(System.currentTimeMillis() - beginTime);
    }
}
