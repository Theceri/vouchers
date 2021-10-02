package com.bezkoder.spring.jpa.postgresql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.bezkoder.spring.jpa.postgresql.RequestResponse.JwtRequest;
import com.bezkoder.spring.jpa.postgresql.RequestResponse.JwtResponse;
import com.bezkoder.spring.jpa.postgresql.model.UserCred;
import com.bezkoder.spring.jpa.postgresql.model.Voucher;
import com.bezkoder.spring.jpa.postgresql.repository.UserRepository;
import com.bezkoder.spring.jpa.postgresql.repository.VoucherRepository;
import com.bezkoder.spring.jpa.postgresql.service.JwtUserDetailsService;
import com.bezkoder.spring.jpa.postgresql.util.JwtTokenUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin("http://localhost:8081")
@RestController
@RequestMapping("/api")
public class VoucherController {

	private static final String SECURED_TEXT = null;
	
	@Autowired
	VoucherRepository voucherRepository;

	@Autowired
	UserRepository userRepository;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	 @GetMapping(path="/secured")
	    public @ResponseBody String getSecured() {
	        System.out.println("GET successfully called on /secured resource");
	        return SECURED_TEXT;
	    }

	@GetMapping("/vouchersByName/{username}")
	public ResponseEntity<List<Voucher>> getAllVouchers(@PathVariable("username") String username,@RequestParam(required = false) String title) {
		try {
			List<Voucher> vouchers = new ArrayList<Voucher>();

			if (title == null)
				voucherRepository.findByPublishedAndUsername(true,username).forEach(vouchers::add);
			else
				voucherRepository.findByTitleContaining(title).forEach(vouchers::add);

			if (vouchers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(vouchers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/vouchers/{id}")
	public ResponseEntity<Voucher> getVoucherById(@PathVariable("id") long id) {
		Optional<Voucher> voucherData = voucherRepository.findById(id);

		if (voucherData.isPresent()) {
			return new ResponseEntity<>(voucherData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/vouchers")
	public ResponseEntity<Voucher> createVoucher(@RequestBody Voucher voucher) {
		try {
			Voucher _voucher = voucherRepository.save(new Voucher(voucher.getTitle(),
					voucher.getAmount(), voucher.getMail(), false));
			return new ResponseEntity<>(_voucher, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PutMapping("/vouchers/{id}")
	public ResponseEntity<Voucher> updateVoucher(@PathVariable("id") long id, @RequestBody Voucher voucher) {
		Optional<Voucher> voucherData = voucherRepository.findById(id);

		if (voucherData.isPresent()) {
			Voucher _voucher = voucherData.get();
			_voucher.setTitle(voucher.getTitle());
			_voucher.setPublished(voucher.isPublished());
			return new ResponseEntity<>(voucherRepository.save(_voucher), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/activate/{id}")
	public ResponseEntity<Voucher> getActivateVouchers(@PathVariable("id") long id, @RequestParam("username")String username) {
		Optional<Voucher> voucherData = voucherRepository.findById(id);
		if (voucherData.isPresent()) {
			Voucher _voucher = voucherData.get();
			if (_voucher.isPublished()) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			_voucher.setPublished(true);
			_voucher.setUsername(username);
			return new ResponseEntity<>(voucherRepository.save(_voucher), HttpStatus.OK);
		} else {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/vouchers/{id}")
	public ResponseEntity<HttpStatus> deleteVoucher(@PathVariable("id") long id) {
		try {
			voucherRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/vouchers")
	public ResponseEntity<HttpStatus> deleteAllVouchers() {
		try {
			voucherRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/vouchers/published/{username}")
	public ResponseEntity<List<Voucher>> findByPublished(@PathVariable("username") String username) {
		try {
			List<Voucher> vouchers = voucherRepository.findByPublishedAndUsername(true,username);

			if (vouchers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(vouchers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update")
	public ResponseEntity updateUser(@RequestBody UserCred userCred) {
	 	try{
			UserCred currentUser = userRepository.findUserByUsername(userCred.getUsername());

			UserCred _userCred = userRepository.save(new UserCred(currentUser.getId(),userCred.getUsername(),
					userCred.getPassword(), userCred.getEmail()));
			return new ResponseEntity(_userCred, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/signUp")
	public ResponseEntity signUp(@RequestBody UserCred userCred) {
		try {
			UserCred userCred1 = userRepository.findUserByUsername(userCred.getUsername());
			if (userCred1 == null) {

				UserCred _userCred = userRepository.save(new UserCred(userCred.getUsername(),
						userCred.getPassword(), userCred.getEmail()));
				return new ResponseEntity(_userCred, HttpStatus.CREATED);
			} else{
				return new ResponseEntity("Already Exists", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
