package org.springframework.webflow.samples.booking;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HotelsController {

	private BookingService bookingService;

	public HotelsController() {
		super();
	}

	@Autowired
	public HotelsController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@RequestMapping(value = "/hotels/search", method = RequestMethod.GET)
	public void search(Principal currentUser, Model model) {
		if (currentUser != null) {
			List<Booking> booking = bookingService.findBookings(currentUser.getName());
			model.addAttribute(booking);
		}
		model.addAttribute("searchCriteria", new SearchCriteria());
	}

	@RequestMapping(value = "/hotels", method = RequestMethod.GET)
	public String list(SearchCriteria criteria, Model model) {
		// List<Hotel> hotels = bookingService.findHotels(criteria);
		// model.addAttribute(hotels);
		return "hotels/list";
	}

	@RequestMapping(value = "/hotels/{id}", method = RequestMethod.GET)
	// @NavigationOutcomeMapping("select")
	public String show(@PathVariable Long id, Model model) {
		model.addAttribute(bookingService.findHotelById(id));
		return "hotels/show";
	}

	// @NavigationOutcomeMapping("delete")
	@RequestMapping(value = "/bookings/{id}", method = RequestMethod.DELETE)
	public String deleteBooking(@PathVariable Long id) {
		// bookingService.cancelBooking(id);
		return "redirect:../hotels/search";
	}

	// // @NavigationCase("book")
	// public String book() {
	// }

}
