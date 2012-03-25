package com.farata.example.service;

import java.util.List;

import clear.cdb.extjs.annotations.CX_JSFillMethod;
import clear.cdb.extjs.annotations.CX_JSGenerateSample;
import clear.cdb.extjs.annotations.CX_JSGenerateStore;
import clear.cdb.extjs.annotations.CX_JSService;

import com.farata.example.dto.CompanyDTO;

@CX_JSService
public interface ICompanyService {
	@CX_JSGenerateSample
	@CX_JSGenerateStore
	@CX_JSFillMethod(autoSyncEnabled = true)
	List<CompanyDTO> getCompanies();
}
