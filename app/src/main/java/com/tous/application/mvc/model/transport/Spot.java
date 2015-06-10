package com.tous.application.mvc.model.transport;


import com.moka.framework.database.DataObject;


public class Spot extends DataObject {

	private long id = -1;
	private long planId = -1;
	private long serverId = -1;
	private String name;
	private String content;
	private long startAt;
	private long endAt;
	private String address;
	private String site;
	private String type;
	private int dirtyFlag = 1;
	private long updatedAt;
	private long createdAt;

	public long getId() {

		return id;
	}

	public void setId( long id ) {

		this.id = id;
	}

	public long getPlanId() {

		return planId;
	}

	public void setPlanId( long planId ) {

		this.planId = planId;
	}

	public long getServerId() {

		return serverId;
	}

	public void setServerId( long serverId ) {

		this.serverId = serverId;
	}

	public String getName() {

		return name;
	}

	public void setName( String name ) {

		this.name = name;
	}

	public String getContent() {

		return content;
	}

	public void setContent( String content ) {

		this.content = content;
	}

	public long getStartAt() {

		return startAt;
	}

	public void setStartAt( long startAt ) {

		this.startAt = startAt;
	}

	public long getEndAt() {

		return endAt;
	}

	public void setEndAt( long endAt ) {

		this.endAt = endAt;
	}

	public String getAddress() {

		return address;
	}

	public void setAddress( String address ) {

		this.address = address;
	}

	public String getSite() {

		return site;
	}

	public void setSite( String site ) {

		this.site = site;
	}

	public String getType() {

		return type;
	}

	public void setType( String type ) {

		this.type = type;
	}

	public int getDirtyFlag() {

		return dirtyFlag;
	}

	public void setDirtyFlag( int dirtyFlag ) {

		this.dirtyFlag = dirtyFlag;
	}

	public long getUpdatedAt() {

		return updatedAt;
	}

	public void setUpdatedAt( long updatedAt ) {

		this.updatedAt = updatedAt;
	}

	public long getCreatedAt() {

		return createdAt;
	}

	public void setCreatedAt( long createdAt ) {

		this.createdAt = createdAt;
	}

	public boolean isSynced() {

		return serverId != -1;
	}

}
