package br.dev.amvs.jasked.sessionbeans;

import java.util.List;

public abstract class PublishableObjectFacade<T> extends AbstractFacade<T>{

	public PublishableObjectFacade(Class<T> entityClass) {
		super(entityClass);
		
	}

	public abstract List<T> findAllPublished();
}
