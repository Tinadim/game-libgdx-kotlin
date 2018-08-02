// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: screen.proto

package com.reis.game.prototypes;

public final class ScreenProto {
  private ScreenProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ScreenDataOrBuilder extends
      // @@protoc_insertion_point(interface_extends:ScreenData)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string backgroundMusicName = 1;</code>
     */
    java.lang.String getBackgroundMusicName();
    /**
     * <code>string backgroundMusicName = 1;</code>
     */
    com.google.protobuf.ByteString
        getBackgroundMusicNameBytes();

    /**
     * <code>string tileMapName = 2;</code>
     */
    java.lang.String getTileMapName();
    /**
     * <code>string tileMapName = 2;</code>
     */
    com.google.protobuf.ByteString
        getTileMapNameBytes();

    /**
     * <code>repeated .EntityData entityData = 3;</code>
     */
    java.util.List<com.reis.game.prototypes.EntityTypeProto.EntityData> 
        getEntityDataList();
    /**
     * <code>repeated .EntityData entityData = 3;</code>
     */
    com.reis.game.prototypes.EntityTypeProto.EntityData getEntityData(int index);
    /**
     * <code>repeated .EntityData entityData = 3;</code>
     */
    int getEntityDataCount();
    /**
     * <code>repeated .EntityData entityData = 3;</code>
     */
    java.util.List<? extends com.reis.game.prototypes.EntityTypeProto.EntityDataOrBuilder> 
        getEntityDataOrBuilderList();
    /**
     * <code>repeated .EntityData entityData = 3;</code>
     */
    com.reis.game.prototypes.EntityTypeProto.EntityDataOrBuilder getEntityDataOrBuilder(
        int index);
  }
  /**
   * Protobuf type {@code ScreenData}
   */
  public  static final class ScreenData extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:ScreenData)
      ScreenDataOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use ScreenData.newBuilder() to construct.
    private ScreenData(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private ScreenData() {
      backgroundMusicName_ = "";
      tileMapName_ = "";
      entityData_ = java.util.Collections.emptyList();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private ScreenData(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownFieldProto3(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();

              backgroundMusicName_ = s;
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              tileMapName_ = s;
              break;
            }
            case 26: {
              if (!((mutable_bitField0_ & 0x00000004) == 0x00000004)) {
                entityData_ = new java.util.ArrayList<com.reis.game.prototypes.EntityTypeProto.EntityData>();
                mutable_bitField0_ |= 0x00000004;
              }
              entityData_.add(
                  input.readMessage(com.reis.game.prototypes.EntityTypeProto.EntityData.parser(), extensionRegistry));
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000004) == 0x00000004)) {
          entityData_ = java.util.Collections.unmodifiableList(entityData_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.reis.game.prototypes.ScreenProto.internal_static_ScreenData_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.reis.game.prototypes.ScreenProto.internal_static_ScreenData_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.reis.game.prototypes.ScreenProto.ScreenData.class, com.reis.game.prototypes.ScreenProto.ScreenData.Builder.class);
    }

    private int bitField0_;
    public static final int BACKGROUNDMUSICNAME_FIELD_NUMBER = 1;
    private volatile java.lang.Object backgroundMusicName_;
    /**
     * <code>string backgroundMusicName = 1;</code>
     */
    public java.lang.String getBackgroundMusicName() {
      java.lang.Object ref = backgroundMusicName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        backgroundMusicName_ = s;
        return s;
      }
    }
    /**
     * <code>string backgroundMusicName = 1;</code>
     */
    public com.google.protobuf.ByteString
        getBackgroundMusicNameBytes() {
      java.lang.Object ref = backgroundMusicName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        backgroundMusicName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TILEMAPNAME_FIELD_NUMBER = 2;
    private volatile java.lang.Object tileMapName_;
    /**
     * <code>string tileMapName = 2;</code>
     */
    public java.lang.String getTileMapName() {
      java.lang.Object ref = tileMapName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        tileMapName_ = s;
        return s;
      }
    }
    /**
     * <code>string tileMapName = 2;</code>
     */
    public com.google.protobuf.ByteString
        getTileMapNameBytes() {
      java.lang.Object ref = tileMapName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        tileMapName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ENTITYDATA_FIELD_NUMBER = 3;
    private java.util.List<com.reis.game.prototypes.EntityTypeProto.EntityData> entityData_;
    /**
     * <code>repeated .EntityData entityData = 3;</code>
     */
    public java.util.List<com.reis.game.prototypes.EntityTypeProto.EntityData> getEntityDataList() {
      return entityData_;
    }
    /**
     * <code>repeated .EntityData entityData = 3;</code>
     */
    public java.util.List<? extends com.reis.game.prototypes.EntityTypeProto.EntityDataOrBuilder> 
        getEntityDataOrBuilderList() {
      return entityData_;
    }
    /**
     * <code>repeated .EntityData entityData = 3;</code>
     */
    public int getEntityDataCount() {
      return entityData_.size();
    }
    /**
     * <code>repeated .EntityData entityData = 3;</code>
     */
    public com.reis.game.prototypes.EntityTypeProto.EntityData getEntityData(int index) {
      return entityData_.get(index);
    }
    /**
     * <code>repeated .EntityData entityData = 3;</code>
     */
    public com.reis.game.prototypes.EntityTypeProto.EntityDataOrBuilder getEntityDataOrBuilder(
        int index) {
      return entityData_.get(index);
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!getBackgroundMusicNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, backgroundMusicName_);
      }
      if (!getTileMapNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, tileMapName_);
      }
      for (int i = 0; i < entityData_.size(); i++) {
        output.writeMessage(3, entityData_.get(i));
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getBackgroundMusicNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, backgroundMusicName_);
      }
      if (!getTileMapNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, tileMapName_);
      }
      for (int i = 0; i < entityData_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(3, entityData_.get(i));
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.reis.game.prototypes.ScreenProto.ScreenData)) {
        return super.equals(obj);
      }
      com.reis.game.prototypes.ScreenProto.ScreenData other = (com.reis.game.prototypes.ScreenProto.ScreenData) obj;

      boolean result = true;
      result = result && getBackgroundMusicName()
          .equals(other.getBackgroundMusicName());
      result = result && getTileMapName()
          .equals(other.getTileMapName());
      result = result && getEntityDataList()
          .equals(other.getEntityDataList());
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + BACKGROUNDMUSICNAME_FIELD_NUMBER;
      hash = (53 * hash) + getBackgroundMusicName().hashCode();
      hash = (37 * hash) + TILEMAPNAME_FIELD_NUMBER;
      hash = (53 * hash) + getTileMapName().hashCode();
      if (getEntityDataCount() > 0) {
        hash = (37 * hash) + ENTITYDATA_FIELD_NUMBER;
        hash = (53 * hash) + getEntityDataList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.reis.game.prototypes.ScreenProto.ScreenData parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.reis.game.prototypes.ScreenProto.ScreenData parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.reis.game.prototypes.ScreenProto.ScreenData parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.reis.game.prototypes.ScreenProto.ScreenData parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.reis.game.prototypes.ScreenProto.ScreenData parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.reis.game.prototypes.ScreenProto.ScreenData parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.reis.game.prototypes.ScreenProto.ScreenData parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.reis.game.prototypes.ScreenProto.ScreenData parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.reis.game.prototypes.ScreenProto.ScreenData parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.reis.game.prototypes.ScreenProto.ScreenData parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.reis.game.prototypes.ScreenProto.ScreenData parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.reis.game.prototypes.ScreenProto.ScreenData parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.reis.game.prototypes.ScreenProto.ScreenData prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code ScreenData}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:ScreenData)
        com.reis.game.prototypes.ScreenProto.ScreenDataOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.reis.game.prototypes.ScreenProto.internal_static_ScreenData_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.reis.game.prototypes.ScreenProto.internal_static_ScreenData_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.reis.game.prototypes.ScreenProto.ScreenData.class, com.reis.game.prototypes.ScreenProto.ScreenData.Builder.class);
      }

      // Construct using com.reis.game.prototypes.ScreenProto.ScreenData.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
          getEntityDataFieldBuilder();
        }
      }
      public Builder clear() {
        super.clear();
        backgroundMusicName_ = "";

        tileMapName_ = "";

        if (entityDataBuilder_ == null) {
          entityData_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000004);
        } else {
          entityDataBuilder_.clear();
        }
        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.reis.game.prototypes.ScreenProto.internal_static_ScreenData_descriptor;
      }

      public com.reis.game.prototypes.ScreenProto.ScreenData getDefaultInstanceForType() {
        return com.reis.game.prototypes.ScreenProto.ScreenData.getDefaultInstance();
      }

      public com.reis.game.prototypes.ScreenProto.ScreenData build() {
        com.reis.game.prototypes.ScreenProto.ScreenData result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.reis.game.prototypes.ScreenProto.ScreenData buildPartial() {
        com.reis.game.prototypes.ScreenProto.ScreenData result = new com.reis.game.prototypes.ScreenProto.ScreenData(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        result.backgroundMusicName_ = backgroundMusicName_;
        result.tileMapName_ = tileMapName_;
        if (entityDataBuilder_ == null) {
          if (((bitField0_ & 0x00000004) == 0x00000004)) {
            entityData_ = java.util.Collections.unmodifiableList(entityData_);
            bitField0_ = (bitField0_ & ~0x00000004);
          }
          result.entityData_ = entityData_;
        } else {
          result.entityData_ = entityDataBuilder_.build();
        }
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.reis.game.prototypes.ScreenProto.ScreenData) {
          return mergeFrom((com.reis.game.prototypes.ScreenProto.ScreenData)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.reis.game.prototypes.ScreenProto.ScreenData other) {
        if (other == com.reis.game.prototypes.ScreenProto.ScreenData.getDefaultInstance()) return this;
        if (!other.getBackgroundMusicName().isEmpty()) {
          backgroundMusicName_ = other.backgroundMusicName_;
          onChanged();
        }
        if (!other.getTileMapName().isEmpty()) {
          tileMapName_ = other.tileMapName_;
          onChanged();
        }
        if (entityDataBuilder_ == null) {
          if (!other.entityData_.isEmpty()) {
            if (entityData_.isEmpty()) {
              entityData_ = other.entityData_;
              bitField0_ = (bitField0_ & ~0x00000004);
            } else {
              ensureEntityDataIsMutable();
              entityData_.addAll(other.entityData_);
            }
            onChanged();
          }
        } else {
          if (!other.entityData_.isEmpty()) {
            if (entityDataBuilder_.isEmpty()) {
              entityDataBuilder_.dispose();
              entityDataBuilder_ = null;
              entityData_ = other.entityData_;
              bitField0_ = (bitField0_ & ~0x00000004);
              entityDataBuilder_ = 
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                   getEntityDataFieldBuilder() : null;
            } else {
              entityDataBuilder_.addAllMessages(other.entityData_);
            }
          }
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.reis.game.prototypes.ScreenProto.ScreenData parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.reis.game.prototypes.ScreenProto.ScreenData) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.lang.Object backgroundMusicName_ = "";
      /**
       * <code>string backgroundMusicName = 1;</code>
       */
      public java.lang.String getBackgroundMusicName() {
        java.lang.Object ref = backgroundMusicName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          backgroundMusicName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string backgroundMusicName = 1;</code>
       */
      public com.google.protobuf.ByteString
          getBackgroundMusicNameBytes() {
        java.lang.Object ref = backgroundMusicName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          backgroundMusicName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string backgroundMusicName = 1;</code>
       */
      public Builder setBackgroundMusicName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        backgroundMusicName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string backgroundMusicName = 1;</code>
       */
      public Builder clearBackgroundMusicName() {
        
        backgroundMusicName_ = getDefaultInstance().getBackgroundMusicName();
        onChanged();
        return this;
      }
      /**
       * <code>string backgroundMusicName = 1;</code>
       */
      public Builder setBackgroundMusicNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        backgroundMusicName_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object tileMapName_ = "";
      /**
       * <code>string tileMapName = 2;</code>
       */
      public java.lang.String getTileMapName() {
        java.lang.Object ref = tileMapName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          tileMapName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string tileMapName = 2;</code>
       */
      public com.google.protobuf.ByteString
          getTileMapNameBytes() {
        java.lang.Object ref = tileMapName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          tileMapName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string tileMapName = 2;</code>
       */
      public Builder setTileMapName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        tileMapName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string tileMapName = 2;</code>
       */
      public Builder clearTileMapName() {
        
        tileMapName_ = getDefaultInstance().getTileMapName();
        onChanged();
        return this;
      }
      /**
       * <code>string tileMapName = 2;</code>
       */
      public Builder setTileMapNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        tileMapName_ = value;
        onChanged();
        return this;
      }

      private java.util.List<com.reis.game.prototypes.EntityTypeProto.EntityData> entityData_ =
        java.util.Collections.emptyList();
      private void ensureEntityDataIsMutable() {
        if (!((bitField0_ & 0x00000004) == 0x00000004)) {
          entityData_ = new java.util.ArrayList<com.reis.game.prototypes.EntityTypeProto.EntityData>(entityData_);
          bitField0_ |= 0x00000004;
         }
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
          com.reis.game.prototypes.EntityTypeProto.EntityData, com.reis.game.prototypes.EntityTypeProto.EntityData.Builder, com.reis.game.prototypes.EntityTypeProto.EntityDataOrBuilder> entityDataBuilder_;

      /**
       * <code>repeated .EntityData entityData = 3;</code>
       */
      public java.util.List<com.reis.game.prototypes.EntityTypeProto.EntityData> getEntityDataList() {
        if (entityDataBuilder_ == null) {
          return java.util.Collections.unmodifiableList(entityData_);
        } else {
          return entityDataBuilder_.getMessageList();
        }
      }
      /**
       * <code>repeated .EntityData entityData = 3;</code>
       */
      public int getEntityDataCount() {
        if (entityDataBuilder_ == null) {
          return entityData_.size();
        } else {
          return entityDataBuilder_.getCount();
        }
      }
      /**
       * <code>repeated .EntityData entityData = 3;</code>
       */
      public com.reis.game.prototypes.EntityTypeProto.EntityData getEntityData(int index) {
        if (entityDataBuilder_ == null) {
          return entityData_.get(index);
        } else {
          return entityDataBuilder_.getMessage(index);
        }
      }
      /**
       * <code>repeated .EntityData entityData = 3;</code>
       */
      public Builder setEntityData(
          int index, com.reis.game.prototypes.EntityTypeProto.EntityData value) {
        if (entityDataBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureEntityDataIsMutable();
          entityData_.set(index, value);
          onChanged();
        } else {
          entityDataBuilder_.setMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .EntityData entityData = 3;</code>
       */
      public Builder setEntityData(
          int index, com.reis.game.prototypes.EntityTypeProto.EntityData.Builder builderForValue) {
        if (entityDataBuilder_ == null) {
          ensureEntityDataIsMutable();
          entityData_.set(index, builderForValue.build());
          onChanged();
        } else {
          entityDataBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .EntityData entityData = 3;</code>
       */
      public Builder addEntityData(com.reis.game.prototypes.EntityTypeProto.EntityData value) {
        if (entityDataBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureEntityDataIsMutable();
          entityData_.add(value);
          onChanged();
        } else {
          entityDataBuilder_.addMessage(value);
        }
        return this;
      }
      /**
       * <code>repeated .EntityData entityData = 3;</code>
       */
      public Builder addEntityData(
          int index, com.reis.game.prototypes.EntityTypeProto.EntityData value) {
        if (entityDataBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureEntityDataIsMutable();
          entityData_.add(index, value);
          onChanged();
        } else {
          entityDataBuilder_.addMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .EntityData entityData = 3;</code>
       */
      public Builder addEntityData(
          com.reis.game.prototypes.EntityTypeProto.EntityData.Builder builderForValue) {
        if (entityDataBuilder_ == null) {
          ensureEntityDataIsMutable();
          entityData_.add(builderForValue.build());
          onChanged();
        } else {
          entityDataBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .EntityData entityData = 3;</code>
       */
      public Builder addEntityData(
          int index, com.reis.game.prototypes.EntityTypeProto.EntityData.Builder builderForValue) {
        if (entityDataBuilder_ == null) {
          ensureEntityDataIsMutable();
          entityData_.add(index, builderForValue.build());
          onChanged();
        } else {
          entityDataBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .EntityData entityData = 3;</code>
       */
      public Builder addAllEntityData(
          java.lang.Iterable<? extends com.reis.game.prototypes.EntityTypeProto.EntityData> values) {
        if (entityDataBuilder_ == null) {
          ensureEntityDataIsMutable();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(
              values, entityData_);
          onChanged();
        } else {
          entityDataBuilder_.addAllMessages(values);
        }
        return this;
      }
      /**
       * <code>repeated .EntityData entityData = 3;</code>
       */
      public Builder clearEntityData() {
        if (entityDataBuilder_ == null) {
          entityData_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000004);
          onChanged();
        } else {
          entityDataBuilder_.clear();
        }
        return this;
      }
      /**
       * <code>repeated .EntityData entityData = 3;</code>
       */
      public Builder removeEntityData(int index) {
        if (entityDataBuilder_ == null) {
          ensureEntityDataIsMutable();
          entityData_.remove(index);
          onChanged();
        } else {
          entityDataBuilder_.remove(index);
        }
        return this;
      }
      /**
       * <code>repeated .EntityData entityData = 3;</code>
       */
      public com.reis.game.prototypes.EntityTypeProto.EntityData.Builder getEntityDataBuilder(
          int index) {
        return getEntityDataFieldBuilder().getBuilder(index);
      }
      /**
       * <code>repeated .EntityData entityData = 3;</code>
       */
      public com.reis.game.prototypes.EntityTypeProto.EntityDataOrBuilder getEntityDataOrBuilder(
          int index) {
        if (entityDataBuilder_ == null) {
          return entityData_.get(index);  } else {
          return entityDataBuilder_.getMessageOrBuilder(index);
        }
      }
      /**
       * <code>repeated .EntityData entityData = 3;</code>
       */
      public java.util.List<? extends com.reis.game.prototypes.EntityTypeProto.EntityDataOrBuilder> 
           getEntityDataOrBuilderList() {
        if (entityDataBuilder_ != null) {
          return entityDataBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(entityData_);
        }
      }
      /**
       * <code>repeated .EntityData entityData = 3;</code>
       */
      public com.reis.game.prototypes.EntityTypeProto.EntityData.Builder addEntityDataBuilder() {
        return getEntityDataFieldBuilder().addBuilder(
            com.reis.game.prototypes.EntityTypeProto.EntityData.getDefaultInstance());
      }
      /**
       * <code>repeated .EntityData entityData = 3;</code>
       */
      public com.reis.game.prototypes.EntityTypeProto.EntityData.Builder addEntityDataBuilder(
          int index) {
        return getEntityDataFieldBuilder().addBuilder(
            index, com.reis.game.prototypes.EntityTypeProto.EntityData.getDefaultInstance());
      }
      /**
       * <code>repeated .EntityData entityData = 3;</code>
       */
      public java.util.List<com.reis.game.prototypes.EntityTypeProto.EntityData.Builder> 
           getEntityDataBuilderList() {
        return getEntityDataFieldBuilder().getBuilderList();
      }
      private com.google.protobuf.RepeatedFieldBuilderV3<
          com.reis.game.prototypes.EntityTypeProto.EntityData, com.reis.game.prototypes.EntityTypeProto.EntityData.Builder, com.reis.game.prototypes.EntityTypeProto.EntityDataOrBuilder> 
          getEntityDataFieldBuilder() {
        if (entityDataBuilder_ == null) {
          entityDataBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
              com.reis.game.prototypes.EntityTypeProto.EntityData, com.reis.game.prototypes.EntityTypeProto.EntityData.Builder, com.reis.game.prototypes.EntityTypeProto.EntityDataOrBuilder>(
                  entityData_,
                  ((bitField0_ & 0x00000004) == 0x00000004),
                  getParentForChildren(),
                  isClean());
          entityData_ = null;
        }
        return entityDataBuilder_;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFieldsProto3(unknownFields);
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:ScreenData)
    }

    // @@protoc_insertion_point(class_scope:ScreenData)
    private static final com.reis.game.prototypes.ScreenProto.ScreenData DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.reis.game.prototypes.ScreenProto.ScreenData();
    }

    public static com.reis.game.prototypes.ScreenProto.ScreenData getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<ScreenData>
        PARSER = new com.google.protobuf.AbstractParser<ScreenData>() {
      public ScreenData parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new ScreenData(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<ScreenData> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<ScreenData> getParserForType() {
      return PARSER;
    }

    public com.reis.game.prototypes.ScreenProto.ScreenData getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ScreenData_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ScreenData_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014screen.proto\032\014entity.proto\"_\n\nScreenDa" +
      "ta\022\033\n\023backgroundMusicName\030\001 \001(\t\022\023\n\013tileM" +
      "apName\030\002 \001(\t\022\037\n\nentityData\030\003 \003(\0132\013.Entit" +
      "yDataB\'\n\030com.reis.game.prototypesB\013Scree" +
      "nProtob\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.reis.game.prototypes.EntityTypeProto.getDescriptor(),
        }, assigner);
    internal_static_ScreenData_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ScreenData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ScreenData_descriptor,
        new java.lang.String[] { "BackgroundMusicName", "TileMapName", "EntityData", });
    com.reis.game.prototypes.EntityTypeProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}