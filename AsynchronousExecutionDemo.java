package com;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsynchronousExecutionDemo {
    public static String startCase(String procedureName, String caseDescription, String dstInParam) {
        ExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture<String> future = new CompletableFuture<>();
          CompletableFuture.supplyAsync(() -> {
                        return ITXStaffwareHandler.getInstance().startCase(procedureName, caseDescription, dstInParam);
                }, executorService)
                .whenComplete((response, error) -> {
                    printMessageOrException(error, response, executorService,future);
        });
        return printMessageOrException(future);
    }


    public static String triggerCase(long caseNumber, String procedureName, String stepName, String dstxInParam) {
        ExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture<String> future = new CompletableFuture<>();
        CompletableFuture.supplyAsync(() -> {
                        return ITXStaffwareHandler.getInstance().triggerCase(caseNumber, procedureName, stepName, dstxInParam);
                }, executorService)
                .whenComplete((response, error) -> {
                    printMessageOrException(error, response, executorService,future);
                });
        return printMessageOrException(future);
    }

    public static String findExistingCases(String procedureName, String filter) {
        ExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture<String> future = new CompletableFuture<>();
        CompletableFuture.supplyAsync(() -> {
                        return ITXStaffwareHandler.getInstance().findExistingCases(procedureName, filter);

                }, executorService)
                .whenComplete((response, error) -> {
                    printMessageOrException(error, response, executorService,future);
                });
        return printMessageOrException(future);

    }


    private static CompletableFuture<String> printMessageOrException(Throwable error, String response, ExecutorService executorService, CompletableFuture<String> future) {
        executorService.shutdownNow();
        if (error == null) {
            future.complete(response);
        } else {
            future.completeExceptionally(error);
        }
        return future;
    }

    public static String printMessageOrException(CompletableFuture<String> future)
    {
        String result="";
        try {
            result = future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Exception while getting the result of the future. " + e.getMessage());
        }
        return result;
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
                    System.out.println( startCase(args[1],args[2],args[3]));
                    break;
                case "2":
                    System.out.println(triggerCase(Long.parseLong(args[1]),args[2],args[3],args[4]));
                    break;
                case "3":
                    System.out.println(findExistingCases(args[1],args[2]));
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
            System.out.println( startCase("procedureName", "caseDescription", "dstInParam"));
             System.out.println( triggerCase(1L, "procedureName", "caseDescription", "dstInParam"));
             System.out.println( findExistingCases("procedureName", "filter"));
        }

        long end=System.currentTimeMillis();
        System.out.println("Elapsed Time:" +(end - start));
    }
}
