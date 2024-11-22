//package lk.ac.iit.RealTimeEventTicketing.Controllers;
//
//import lk.ac.iit.RealTimeEventTicketing.Service.TicketPoolService;
//import lk.ac.iit.RealTimeEventTicketing.model.;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
////@RestController
////@RequestMapping("/TicketPool")
////public class TicketPoolControllers {
//    private final TicketPoolService ticketPoolService;
//
//    @Autowired
//    public TicketPoolControllers(TicketPoolService ticketPoolService) {
//        this.ticketPoolService = ticketPoolService;
//    }
//}
//
//    @GetMapping("/all")
//    public ResponseEntity<List<TicketPool>>getAllTicketPools() {
//        List<TicketPool>ticketPools=ticketPoolService.findAllTicketPools();
//        return new ResponseEntity<>(ticketPools, HttpStatus.OK);
//    }
//
//        @GetMapping("/find/{ticketId}")
//        public ResponseEntity<TicketPool> findTicketById(@PathVariable("ticketId") Long ticketId){
//            TicketPool ticketPool = ticketPoolService.getTicketPoolById(ticketId);
//            return new ResponseEntity<>(ticketPool, HttpStatus.OK);
//        }
//
//
//        @PostMapping("/add")
//        public ResponseEntity<TicketPool> addTicketPool(@RequestBody TicketPool ticketPool) {
//         TicketPool newTicketPool = ticketPoolService.addTicketPool(ticketPool);
//         return new ResponseEntity<>(newTicketPool, HttpStatus.CREATED);
//        }
//
//        @PutMapping("/update")
//        public ResponseEntity<Vendor> updateTicketPool(@RequestBody TicketPool ticketPool) {
//            TicketPool newTicketPool = ticketPoolService.updateTicketPool(ticketPool);
//            return new ResponseEntity<>(TicketPool, HttpStatus.OK);
//        }
//        @DeleteMapping("/delete/{vendorId}")
//        public ResponseEntity<Vendor> deleteVendor(@PathVariable("vendorId") Long vendorId) {
//            vendorService.deleteVendorById(vendorId);
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//
//    }
//
//
//}
//}
