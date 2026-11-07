   package com.sumanth.order_tracking;

   import java.util.Map;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.RestController;

   @RestController
   public class VersionController {

       @GetMapping("/version")
       public Map<String, String> version() {
           return Map.of("version", "0.2");
       }
   }