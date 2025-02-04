package singleton;

public class Person<T> {
    private T value;

    public Person(T value) {
        this.value= value;
    }

    public T getValue() {
        return  this.value;
    }
    public <E> E getValue(Class<E> clazz) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }
}
