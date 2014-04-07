/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package jsa.test.backward.v1.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackwardCompAPI {

  public interface Iface {

    public void save(String name1, String name2) throws org.apache.thrift.TException;

  }

  public interface AsyncIface {

    public void save(String name1, String name2, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException;

  }

  public static class Client extends org.apache.thrift.TServiceClient implements Iface {
    public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {
      public Factory() {}
      public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
        return new Client(prot);
      }
      public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
        return new Client(iprot, oprot);
      }
    }

    public Client(org.apache.thrift.protocol.TProtocol prot)
    {
      super(prot, prot);
    }

    public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
      super(iprot, oprot);
    }

    public void save(String name1, String name2) throws org.apache.thrift.TException
    {
      send_save(name1, name2);
      recv_save();
    }

    public void send_save(String name1, String name2) throws org.apache.thrift.TException
    {
      save_args args = new save_args();
      args.setName1(name1);
      args.setName2(name2);
      sendBase("save", args);
    }

    public void recv_save() throws org.apache.thrift.TException
    {
      save_result result = new save_result();
      receiveBase(result, "save");
      return;
    }

  }
  public static class AsyncClient extends org.apache.thrift.async.TAsyncClient implements AsyncIface {
    public static class Factory implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {
      private org.apache.thrift.async.TAsyncClientManager clientManager;
      private org.apache.thrift.protocol.TProtocolFactory protocolFactory;
      public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
        this.clientManager = clientManager;
        this.protocolFactory = protocolFactory;
      }
      public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport) {
        return new AsyncClient(protocolFactory, clientManager, transport);
      }
    }

    public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.transport.TNonblockingTransport transport) {
      super(protocolFactory, clientManager, transport);
    }

    public void save(String name1, String name2, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException {
      checkReady();
      save_call method_call = new save_call(name1, name2, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class save_call extends org.apache.thrift.async.TAsyncMethodCall {
      private String name1;
      private String name2;
      public save_call(String name1, String name2, org.apache.thrift.async.AsyncMethodCallback resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.name1 = name1;
        this.name2 = name2;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("save", org.apache.thrift.protocol.TMessageType.CALL, 0));
        save_args args = new save_args();
        args.setName1(name1);
        args.setName2(name2);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public void getResult() throws org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        (new Client(prot)).recv_save();
      }
    }

  }

  public static class Processor<I extends Iface> extends org.apache.thrift.TBaseProcessor<I> implements org.apache.thrift.TProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class.getName());
    public Processor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
    }

    protected Processor(I iface, Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends Iface> Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> getProcessMap(Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      processMap.put("save", new save());
      return processMap;
    }

    public static class save<I extends Iface> extends org.apache.thrift.ProcessFunction<I, save_args> {
      public save() {
        super("save");
      }

      public save_args getEmptyArgsInstance() {
        return new save_args();
      }

      protected boolean isOneway() {
        return false;
      }

      public save_result getResult(I iface, save_args args) throws org.apache.thrift.TException {
        save_result result = new save_result();
        iface.save(args.name1, args.name2);
        return result;
      }
    }

  }

  public static class AsyncProcessor<I extends AsyncIface> extends org.apache.thrift.TBaseAsyncProcessor<I> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncProcessor.class.getName());
    public AsyncProcessor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>>()));
    }

    protected AsyncProcessor(I iface, Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends AsyncIface> Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase,?>> getProcessMap(Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      processMap.put("save", new save());
      return processMap;
    }

    public static class save<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, save_args, Void> {
      public save() {
        super("save");
      }

      public save_args getEmptyArgsInstance() {
        return new save_args();
      }

      public AsyncMethodCallback<Void> getResultHandler(final AsyncFrameBuffer fb, final int seqid) {
        final org.apache.thrift.AsyncProcessFunction fcall = this;
        return new AsyncMethodCallback<Void>() { 
          public void onComplete(Void o) {
            save_result result = new save_result();
            try {
              fcall.sendResponse(fb,result, org.apache.thrift.protocol.TMessageType.REPLY,seqid);
              return;
            } catch (Exception e) {
              LOGGER.error("Exception writing to internal frame buffer", e);
            }
            fb.close();
          }
          public void onError(Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TBase msg;
            save_result result = new save_result();
            {
              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
              msg = (org.apache.thrift.TBase)new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.INTERNAL_ERROR, e.getMessage());
            }
            try {
              fcall.sendResponse(fb,msg,msgType,seqid);
              return;
            } catch (Exception ex) {
              LOGGER.error("Exception writing to internal frame buffer", ex);
            }
            fb.close();
          }
        };
      }

      protected boolean isOneway() {
        return false;
      }

      public void start(I iface, save_args args, org.apache.thrift.async.AsyncMethodCallback<Void> resultHandler) throws TException {
        iface.save(args.name1, args.name2,resultHandler);
      }
    }

  }

  public static class save_args implements org.apache.thrift.TBase<save_args, save_args._Fields>, java.io.Serializable, Cloneable, Comparable<save_args>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("save_args");

    private static final org.apache.thrift.protocol.TField NAME1_FIELD_DESC = new org.apache.thrift.protocol.TField("name1", org.apache.thrift.protocol.TType.STRING, (short)1);
    private static final org.apache.thrift.protocol.TField NAME2_FIELD_DESC = new org.apache.thrift.protocol.TField("name2", org.apache.thrift.protocol.TType.STRING, (short)2);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new save_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new save_argsTupleSchemeFactory());
    }

    private String name1; // required
    private String name2; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      NAME1((short)1, "name1"),
      NAME2((short)2, "name2");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // NAME1
            return NAME1;
          case 2: // NAME2
            return NAME2;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.NAME1, new org.apache.thrift.meta_data.FieldMetaData("name1", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
      tmpMap.put(_Fields.NAME2, new org.apache.thrift.meta_data.FieldMetaData("name2", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(save_args.class, metaDataMap);
    }

    public save_args() {
    }

    public save_args(
      String name1,
      String name2)
    {
      this();
      this.name1 = name1;
      this.name2 = name2;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public save_args(save_args other) {
      if (other.isSetName1()) {
        this.name1 = other.name1;
      }
      if (other.isSetName2()) {
        this.name2 = other.name2;
      }
    }

    public save_args deepCopy() {
      return new save_args(this);
    }

    @Override
    public void clear() {
      this.name1 = null;
      this.name2 = null;
    }

    public String getName1() {
      return this.name1;
    }

    public void setName1(String name1) {
      this.name1 = name1;
    }

    public void unsetName1() {
      this.name1 = null;
    }

    /** Returns true if field name1 is set (has been assigned a value) and false otherwise */
    public boolean isSetName1() {
      return this.name1 != null;
    }

    public void setName1IsSet(boolean value) {
      if (!value) {
        this.name1 = null;
      }
    }

    public String getName2() {
      return this.name2;
    }

    public void setName2(String name2) {
      this.name2 = name2;
    }

    public void unsetName2() {
      this.name2 = null;
    }

    /** Returns true if field name2 is set (has been assigned a value) and false otherwise */
    public boolean isSetName2() {
      return this.name2 != null;
    }

    public void setName2IsSet(boolean value) {
      if (!value) {
        this.name2 = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case NAME1:
        if (value == null) {
          unsetName1();
        } else {
          setName1((String)value);
        }
        break;

      case NAME2:
        if (value == null) {
          unsetName2();
        } else {
          setName2((String)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case NAME1:
        return getName1();

      case NAME2:
        return getName2();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case NAME1:
        return isSetName1();
      case NAME2:
        return isSetName2();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof save_args)
        return this.equals((save_args)that);
      return false;
    }

    public boolean equals(save_args that) {
      if (that == null)
        return false;

      boolean this_present_name1 = true && this.isSetName1();
      boolean that_present_name1 = true && that.isSetName1();
      if (this_present_name1 || that_present_name1) {
        if (!(this_present_name1 && that_present_name1))
          return false;
        if (!this.name1.equals(that.name1))
          return false;
      }

      boolean this_present_name2 = true && this.isSetName2();
      boolean that_present_name2 = true && that.isSetName2();
      if (this_present_name2 || that_present_name2) {
        if (!(this_present_name2 && that_present_name2))
          return false;
        if (!this.name2.equals(that.name2))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    @Override
    public int compareTo(save_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = Boolean.valueOf(isSetName1()).compareTo(other.isSetName1());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetName1()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name1, other.name1);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetName2()).compareTo(other.isSetName2());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetName2()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name2, other.name2);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("save_args(");
      boolean first = true;

      sb.append("name1:");
      if (this.name1 == null) {
        sb.append("null");
      } else {
        sb.append(this.name1);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("name2:");
      if (this.name2 == null) {
        sb.append("null");
      } else {
        sb.append(this.name2);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class save_argsStandardSchemeFactory implements SchemeFactory {
      public save_argsStandardScheme getScheme() {
        return new save_argsStandardScheme();
      }
    }

    private static class save_argsStandardScheme extends StandardScheme<save_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, save_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // NAME1
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.name1 = iprot.readString();
                struct.setName1IsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // NAME2
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.name2 = iprot.readString();
                struct.setName2IsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, save_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.name1 != null) {
          oprot.writeFieldBegin(NAME1_FIELD_DESC);
          oprot.writeString(struct.name1);
          oprot.writeFieldEnd();
        }
        if (struct.name2 != null) {
          oprot.writeFieldBegin(NAME2_FIELD_DESC);
          oprot.writeString(struct.name2);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class save_argsTupleSchemeFactory implements SchemeFactory {
      public save_argsTupleScheme getScheme() {
        return new save_argsTupleScheme();
      }
    }

    private static class save_argsTupleScheme extends TupleScheme<save_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, save_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetName1()) {
          optionals.set(0);
        }
        if (struct.isSetName2()) {
          optionals.set(1);
        }
        oprot.writeBitSet(optionals, 2);
        if (struct.isSetName1()) {
          oprot.writeString(struct.name1);
        }
        if (struct.isSetName2()) {
          oprot.writeString(struct.name2);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, save_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(2);
        if (incoming.get(0)) {
          struct.name1 = iprot.readString();
          struct.setName1IsSet(true);
        }
        if (incoming.get(1)) {
          struct.name2 = iprot.readString();
          struct.setName2IsSet(true);
        }
      }
    }

  }

  public static class save_result implements org.apache.thrift.TBase<save_result, save_result._Fields>, java.io.Serializable, Cloneable, Comparable<save_result>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("save_result");


    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new save_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new save_resultTupleSchemeFactory());
    }


    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
;

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(save_result.class, metaDataMap);
    }

    public save_result() {
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public save_result(save_result other) {
    }

    public save_result deepCopy() {
      return new save_result(this);
    }

    @Override
    public void clear() {
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof save_result)
        return this.equals((save_result)that);
      return false;
    }

    public boolean equals(save_result that) {
      if (that == null)
        return false;

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    @Override
    public int compareTo(save_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
      }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("save_result(");
      boolean first = true;

      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class save_resultStandardSchemeFactory implements SchemeFactory {
      public save_resultStandardScheme getScheme() {
        return new save_resultStandardScheme();
      }
    }

    private static class save_resultStandardScheme extends StandardScheme<save_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, save_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, save_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class save_resultTupleSchemeFactory implements SchemeFactory {
      public save_resultTupleScheme getScheme() {
        return new save_resultTupleScheme();
      }
    }

    private static class save_resultTupleScheme extends TupleScheme<save_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, save_result struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, save_result struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
      }
    }

  }

}