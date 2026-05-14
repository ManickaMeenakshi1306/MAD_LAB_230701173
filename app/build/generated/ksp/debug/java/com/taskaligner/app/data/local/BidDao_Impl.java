package com.taskaligner.app.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BidDao_Impl implements BidDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BidEntity> __insertionAdapterOfBidEntity;

  private final EntityDeletionOrUpdateAdapter<BidEntity> __updateAdapterOfBidEntity;

  public BidDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBidEntity = new EntityInsertionAdapter<BidEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `bids` (`id`,`jobId`,`freelancerId`,`freelancerName`,`amount`,`proposal`,`status`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BidEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getJobId());
        statement.bindString(3, entity.getFreelancerId());
        statement.bindString(4, entity.getFreelancerName());
        statement.bindString(5, entity.getAmount());
        statement.bindString(6, entity.getProposal());
        statement.bindString(7, entity.getStatus());
      }
    };
    this.__updateAdapterOfBidEntity = new EntityDeletionOrUpdateAdapter<BidEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `bids` SET `id` = ?,`jobId` = ?,`freelancerId` = ?,`freelancerName` = ?,`amount` = ?,`proposal` = ?,`status` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BidEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getJobId());
        statement.bindString(3, entity.getFreelancerId());
        statement.bindString(4, entity.getFreelancerName());
        statement.bindString(5, entity.getAmount());
        statement.bindString(6, entity.getProposal());
        statement.bindString(7, entity.getStatus());
        statement.bindString(8, entity.getId());
      }
    };
  }

  @Override
  public Object insertBid(final BidEntity bid, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBidEntity.insert(bid);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateBid(final BidEntity bid, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBidEntity.handle(bid);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<BidEntity>> getBidsForJob(final String jobId) {
    final String _sql = "SELECT * FROM bids WHERE jobId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, jobId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bids"}, new Callable<List<BidEntity>>() {
      @Override
      @NonNull
      public List<BidEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfJobId = CursorUtil.getColumnIndexOrThrow(_cursor, "jobId");
          final int _cursorIndexOfFreelancerId = CursorUtil.getColumnIndexOrThrow(_cursor, "freelancerId");
          final int _cursorIndexOfFreelancerName = CursorUtil.getColumnIndexOrThrow(_cursor, "freelancerName");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfProposal = CursorUtil.getColumnIndexOrThrow(_cursor, "proposal");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<BidEntity> _result = new ArrayList<BidEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BidEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpJobId;
            _tmpJobId = _cursor.getString(_cursorIndexOfJobId);
            final String _tmpFreelancerId;
            _tmpFreelancerId = _cursor.getString(_cursorIndexOfFreelancerId);
            final String _tmpFreelancerName;
            _tmpFreelancerName = _cursor.getString(_cursorIndexOfFreelancerName);
            final String _tmpAmount;
            _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            final String _tmpProposal;
            _tmpProposal = _cursor.getString(_cursorIndexOfProposal);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            _item = new BidEntity(_tmpId,_tmpJobId,_tmpFreelancerId,_tmpFreelancerName,_tmpAmount,_tmpProposal,_tmpStatus);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
