package com.tous.application.mvc.model.spot;


import com.moka.framework.database.DataObject;


public class Transport extends DataObject {

	private long id = -1;
	private long planId = -1;
	private long serverId = -1;
	private String name;
	private String content;
	private long timeAt;
	private String way;
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

	public long getTimeAt() {

		return timeAt;
	}

	public void setTimeAt( long timeAt ) {

		this.timeAt = timeAt;
	}

	public String getWay() {

		return way;
	}

	public void setWay( String way ) {

		this.way = way;
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
