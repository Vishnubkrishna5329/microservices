package com.vishnu.accounts.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetails {

	private Accounts accounts;
	
	private List<Loans> loans;
	
	private List<Cards> cards;
}
