package com.vishnu.accounts.model;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountsProperties {

	private String msg;
	private String buildVersion;
	private Map<String, String> mapDetails;
	private List<String> activeBranches;
}
