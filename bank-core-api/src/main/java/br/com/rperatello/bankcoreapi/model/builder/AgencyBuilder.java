package br.com.rperatello.bankcoreapi.model.builder;

import br.com.rperatello.bankcoreapi.model.Agency;
import br.com.rperatello.bankcoreapi.utils.formatters.StringFormat;

public class AgencyBuilder implements IAgencyBuilder<Agency> {
	
	private Agency builder;

    public AgencyBuilder() {
    	builder = new Agency();
    	SetDefaultValues();
    }

    public AgencyBuilder id(Long id) {
    	if( id == null ) id = 0L;
        builder.setId(id); 
        return this;
    }

    public AgencyBuilder name(String name) {
    	if( name == null ) name = "";
    	builder.setName(StringFormat.removeDoubleSpace(name).toUpperCase());
        return this;
    }

    public AgencyBuilder number(Long number) {
    	if( number == null ) number = 0L;
    	builder.setNumber(number);
        return this;
    }

    @Override
    public Agency build() {
        return builder;
    }
    
    private void SetDefaultValues() {
    	builder.setId(0L); 
        builder.setName("");
        builder.setNumber(0L);
    }    

    public static AgencyBuilder getbuilder() {
    	return new AgencyBuilder();
    }

}

