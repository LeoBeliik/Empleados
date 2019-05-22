package data;

import java.util.function.Consumer;

class TabManager {

    private static TabManager instance;

    private Consumer<String> consumer;

    static TabManager getInstance() {
        if(instance == null)
            instance = new TabManager();
        return instance;
    }

    private TabManager() {
    }

    void register(Consumer<String> consumer) {
        this.consumer = consumer;
    }

    void fireEvent(String string) {
        if(consumer != null)
            consumer.accept(string);
    }
}
