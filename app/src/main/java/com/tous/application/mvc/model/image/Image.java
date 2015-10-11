package com.tous.application.mvc.model.image;


import com.moka.framework.database.DataObject;


public class Image extends DataObject {

	private long id = -1;
	private long userId = -1;
	private long serverId = -1;
	private String imageType;
	private long imageId = -1;
	private String imageName;
	private int dirtyFlag = 1;
	private long updatedAt;
	private long createdAt;

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

	public String getImageType() {

		return imageType;
	}

	public void setImageType( String imageType ) {

		this.imageType = imageType;
	}

	public long getImageId() {

		return imageId;
	}

	public void setImageId( long imageId ) {

		this.imageId = imageId;
	}

	public String getImageName() {

		return imageName;
	}

	public void setImageName( String imageName ) {

		this.imageName = imageName;
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

}
