package tian;

import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import tian.record.RecordService;

import javax.inject.Inject;

@QuarkusMain
public class Main {
    public static void main(String ... args) {
        Quarkus.run(MyApp.class, args);
    }
    
    
    public static class MyApp implements QuarkusApplication {

        @Inject
        RecordService service;

        @Override
        public int run(String... args) throws Exception {
            System.out.println("Do startup logic here");
            service.initialRecords();
            Quarkus.waitForExit();
            return 0;
        }
    }


}
