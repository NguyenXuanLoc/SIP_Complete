/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua2;

import java.io.Serializable;

public class AccountConfig extends PersistentObject implements Serializable {
  private transient long swigCPtr;

  protected AccountConfig(long cPtr, boolean cMemoryOwn) {
    super(pjsua2JNI.AccountConfig_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(AccountConfig obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  @SuppressWarnings("deprecation")
  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        pjsua2JNI.delete_AccountConfig(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public void setPriority(int value) {
    pjsua2JNI.AccountConfig_priority_set(swigCPtr, this, value);
  }

  public int getPriority() {
    return pjsua2JNI.AccountConfig_priority_get(swigCPtr, this);
  }

  public void setIdUri(String value) {
    pjsua2JNI.AccountConfig_idUri_set(swigCPtr, this, value);
  }

  public String getIdUri() {
    return pjsua2JNI.AccountConfig_idUri_get(swigCPtr, this);
  }

  public void setRegConfig(AccountRegConfig value) {
    pjsua2JNI.AccountConfig_regConfig_set(swigCPtr, this, AccountRegConfig.getCPtr(value), value);
  }

  public AccountRegConfig getRegConfig() {
    long cPtr = pjsua2JNI.AccountConfig_regConfig_get(swigCPtr, this);
    return (cPtr == 0) ? null : new AccountRegConfig(cPtr, false);
  }

  public void setSipConfig(AccountSipConfig value) {
    pjsua2JNI.AccountConfig_sipConfig_set(swigCPtr, this, AccountSipConfig.getCPtr(value), value);
  }

  public AccountSipConfig getSipConfig() {
    long cPtr = pjsua2JNI.AccountConfig_sipConfig_get(swigCPtr, this);
    return (cPtr == 0) ? null : new AccountSipConfig(cPtr, false);
  }

  public void setCallConfig(AccountCallConfig value) {
    pjsua2JNI.AccountConfig_callConfig_set(swigCPtr, this, AccountCallConfig.getCPtr(value), value);
  }

  public AccountCallConfig getCallConfig() {
    long cPtr = pjsua2JNI.AccountConfig_callConfig_get(swigCPtr, this);
    return (cPtr == 0) ? null : new AccountCallConfig(cPtr, false);
  }

  public void setPresConfig(AccountPresConfig value) {
    pjsua2JNI.AccountConfig_presConfig_set(swigCPtr, this, AccountPresConfig.getCPtr(value), value);
  }

  public AccountPresConfig getPresConfig() {
    long cPtr = pjsua2JNI.AccountConfig_presConfig_get(swigCPtr, this);
    return (cPtr == 0) ? null : new AccountPresConfig(cPtr, false);
  }

  public void setMwiConfig(AccountMwiConfig value) {
    pjsua2JNI.AccountConfig_mwiConfig_set(swigCPtr, this, AccountMwiConfig.getCPtr(value), value);
  }

  public AccountMwiConfig getMwiConfig() {
    long cPtr = pjsua2JNI.AccountConfig_mwiConfig_get(swigCPtr, this);
    return (cPtr == 0) ? null : new AccountMwiConfig(cPtr, false);
  }

  public void setNatConfig(AccountNatConfig value) {
    pjsua2JNI.AccountConfig_natConfig_set(swigCPtr, this, AccountNatConfig.getCPtr(value), value);
  }

  public AccountNatConfig getNatConfig() {
    long cPtr = pjsua2JNI.AccountConfig_natConfig_get(swigCPtr, this);
    return (cPtr == 0) ? null : new AccountNatConfig(cPtr, false);
  }

  public void setMediaConfig(AccountMediaConfig value) {
    pjsua2JNI.AccountConfig_mediaConfig_set(swigCPtr, this, AccountMediaConfig.getCPtr(value), value);
  }

  public AccountMediaConfig getMediaConfig() {
    long cPtr = pjsua2JNI.AccountConfig_mediaConfig_get(swigCPtr, this);
    return (cPtr == 0) ? null : new AccountMediaConfig(cPtr, false);
  }

  public void setVideoConfig(AccountVideoConfig value) {
    pjsua2JNI.AccountConfig_videoConfig_set(swigCPtr, this, AccountVideoConfig.getCPtr(value), value);
  }

  public AccountVideoConfig getVideoConfig() {
    long cPtr = pjsua2JNI.AccountConfig_videoConfig_get(swigCPtr, this);
    return (cPtr == 0) ? null : new AccountVideoConfig(cPtr, false);
  }

  public void setIpChangeConfig(AccountIpChangeConfig value) {
    pjsua2JNI.AccountConfig_ipChangeConfig_set(swigCPtr, this, AccountIpChangeConfig.getCPtr(value), value);
  }

  public AccountIpChangeConfig getIpChangeConfig() {
    long cPtr = pjsua2JNI.AccountConfig_ipChangeConfig_get(swigCPtr, this);
    return (cPtr == 0) ? null : new AccountIpChangeConfig(cPtr, false);
  }

  public AccountConfig() {
    this(pjsua2JNI.new_AccountConfig(), true);
  }

  public void readObject(ContainerNode node) throws java.lang.Exception {
    pjsua2JNI.AccountConfig_readObject(swigCPtr, this, ContainerNode.getCPtr(node), node);
  }

  public void writeObject(ContainerNode node) throws java.lang.Exception {
    pjsua2JNI.AccountConfig_writeObject(swigCPtr, this, ContainerNode.getCPtr(node), node);
  }

}
