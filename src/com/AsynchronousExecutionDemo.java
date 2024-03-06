package com;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsynchronousExecutionDemo {

    public static void startCase(String procedureName, String caseDescription, String dstInParam) {
        ExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture.supplyAsync(() -> {
            return ITXStaffwareHandler.getInstance().startCase(procedureName, caseDescription, dstInParam);
        }, executorService)
                .whenComplete((result, exception) -> {
            printMessageOrException(exception, result, executorService);
        });
    }

    public static void triggerCase(long caseNumber, String procedureName, String stepName, String dstxInParam) {
        ExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture.supplyAsync(() -> {

            return ITXStaffwareHandler.getInstance().triggerCase(caseNumber, procedureName, stepName, dstxInParam);
        }, executorService)
                .whenComplete((result, exception) -> {
                    printMessageOrException(exception, result, executorService);
                });
    }

    public static void findExistingCases(String procedureName, String filter) {
        ExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture.supplyAsync(() -> {
            return ITXStaffwareHandler.getInstance().findExistingCases(procedureName, filter);
        }, executorService)
        .whenComplete((result, exception) -> {
            printMessageOrException(exception, result, executorService);
        });

    }

    private static void printMessageOrException(Throwable exception, String result, ExecutorService executorService) {
        try {
            System.out.println(result);
        }finally {
            if (exception!=null)
            {
                System.err.println(exception.getMessage());
            }
            System.out.println("Shutting down.");
            executorService.shutdownNow();
            System.out.println("shutdown.");
        }

    }

    public static void close()throws Exception{
        ITXStaffwareHandler.getInstance().closeHandler();
    }
    public static void main_demo(String[] args) {
        // TODO Auto-generated method stub
        System.clearProperty("sw_log4j_config");
        long start=System.currentTimeMillis();
        try {
            //1.start 2.find 3.trigger

            switch(args[0]) {
                case "1":
                     startCase(args[1],args[2],args[3]);
                    break;
                case "2":
                    triggerCase(Long.parseLong(args[1]),args[2],args[3],args[4]);
                    break;
                case "3":
                    findExistingCases(args[1],args[2]);
                    break;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                close();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }

        long end=System.currentTimeMillis();
        System.out.println("Elapsed Time:" +(end - start));
    }


    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            startCase("procedureName", "caseDescription", "dstInParam");
            triggerCase(1L, "procedureName", "caseDescription", "dstInParam");
            findExistingCases("procedureName", "filter");
        }

        long end=System.currentTimeMillis();
        System.out.println("Elapsed Time:" +(end - start));
    }
}
