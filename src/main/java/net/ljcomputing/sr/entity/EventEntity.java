package net.ljcomputing.sr.entity;

import net.ljcomputing.core.domain.Domain;
import net.ljcomputing.core.entity.AbstractPersistedEntity;
import net.ljcomputing.core.entity.PersistedEntity;
import net.ljcomputing.gson.annotation.ExcludeFromJson;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Event entity.
 */
@Entity
@Table(name = "event")
// @Cacheable
public class EventEntity extends AbstractPersistedEntity
    implements PersistedEntity, Domain {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 8819001525488173938L;

  /** The description. */
  private String description;

  /** The end time. */
  private Date endTime;

  /** The start time. */
  private Date startTime;

  /** The ticket number. */
  private String ticketNumber;

  /** The activity. */
  @ExcludeFromJson
  private ActivityEntity activity;

  /**
   * Gets the description.
   *
   * @return the description
   */
  @Column(name = "description")
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param description the new description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the end time.
   *
   * @return the end time
   */
  @Column(name = "end_time")
  @Temporal(TemporalType.TIMESTAMP)
  public Date getEndTime() {
    return endTime;
  }

  /**
   * Sets the end time.
   *
   * @param endTime the new end time
   */
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  /**
   * Gets the start time.
   *
   * @return the start time
   */
  @Column(name = "start_time")
  @Temporal(TemporalType.TIMESTAMP)
  public Date getStartTime() {
    return startTime;
  }

  /**
   * Sets the start time.
   *
   * @param startTime the new start time
   */
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  /**
   * Gets the ticket number.
   *
   * @return the ticket number
   */
  @Column(name = "ticket_number")
  public String getTicketNumber() {
    return ticketNumber;
  }

  /**
   * Sets the ticket number.
   *
   * @param ticketNumber the new ticket number
   */
  public void setTicketNumber(String ticketNumber) {
    this.ticketNumber = ticketNumber;
  }

  /**
   * Gets the activity.
   *
   * @return the activity
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "activity_uuid", referencedColumnName = "uuid")
  public ActivityEntity getActivity() {
    return activity;
  }

  /**
   * Sets the activity.
   *
   * @param activity the new activity
   */
  public void setActivity(ActivityEntity activity) {
    this.activity = activity;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.ljcomputing.core.entity.AbstractDomain#toString()
   */
  @Override
  public String toString() {
    return "EventEntity [description=" + description + ", endTime=" + endTime
        + ", startTime=" + startTime + ", ticketNumber=" + ticketNumber
        + ", getCreatedTs()=" + getCreatedTs() + ", getModifiedTs()="
        + getModifiedTs() + ", getUuid()=" + getUuid() + "]";
  }
}
