package com.tous.application.mvc.model.plan;


import com.moka.framework.database.DataObject;
import com.tous.application.util.DateUtil;


public class Plan extends DataObject {

	private long id = -1;
	private long userId = -1;
	private long serverId = -1;
	private String name;
	private String content;
	private int startDate;
	private int endDate;
	private int dirtyFlag = 1;
	private long createdAt;
	private long updatedAt;

	public long getId() {

		return id;
	}

	public void setId( long id ) {

		this.id = id;
	}

	public long getUserId() {

		return userId;
	}

	public void setUserId( long userId ) {

		this.userId = userId;
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

	public int getStartDate() {

		return startDate;
	}

	public void setStartDate( int startDate ) {

		this.startDate = startDate;
	}

	public int getEndDate() {

		return endDate;
	}

	public void setEndDate( int endDate ) {

		this.endDate = endDate;
	}

	public int getDirtyFlag() {

		return dirtyFlag;
	}

	public void setDirtyFlag( int dirtyFlag ) {

		this.dirtyFlag = dirtyFlag;
	}

	public long getCreatedAt() {

		return createdAt;
	}

	public void setCreatedAt( long createdAt ) {

		this.createdAt = createdAt;
	}

	public long getUpdatedAt() {

		return updatedAt;
	}

	public void setUpdatedAt( long updatedAt ) {

		this.updatedAt = updatedAt;
	}

	public boolean isSynced() {

		return serverId != -1;
	}

	public int getPlanDayCount() {

		return DateUtil.getDiffDayCount( startDate, endDate ) + 1;
	}

	public int getDayCount( int currentDate ) {

		return DateUtil.getDiffDayCount( startDate, currentDate ) + 1;
	}

	public boolean isPlaningDate( int currentDate ) {

		int planDayCount = getPlanDayCount();
		int dayCount = getDayCount( currentDate );

		if ( 0 < dayCount && planDayCount >= dayCount )
			return true;
		else
			return false;
	}

}
