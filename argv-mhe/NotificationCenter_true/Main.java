// SPDX-FileCopyrightText: Copyright (C) 2019-2025 qwq233 <qwq233@qwq2333.top>
// SPDX-License-Identifier: GPL-2.0-or-later
// SPDX-FileCopyrightText: Copyright (C) 2026 The ARG-V Project

/** filtered and transformed by ARG-V */

/*
 * Copyright (C) 2019-2025 qwq233 <qwq233@qwq2333.top>
 * https://github.com/qwq233/Nullgram
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
import org.sosy_lab.sv_benchmarks.Verifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class Main {

    private static long EXPIRE_NOTIFICATIONS_TIME = 5017;
    private static int totalEvents = 1;

    public static int onUpdateLoginToken = totalEvents++;
    public static int didReceiveNewMessages = totalEvents++;
    public static int updateInterfaces = totalEvents++;
    public static int dialogsNeedReload = totalEvents++;
    public static int closeChats = totalEvents++;
    public static int closeChatActivity = totalEvents++;
    public static int closeProfileActivity = totalEvents++;
    public static int messagesDeleted = totalEvents++;
    public static int historyCleared = totalEvents++;
    public static int messagesRead = totalEvents++;
    public static int threadMessagesRead = totalEvents++;
    public static int monoForumMessagesRead = totalEvents++;
    public static int commentsRead = totalEvents++;
    public static int changeRepliesCounter = totalEvents++;
    public static int messagesDidLoad = totalEvents++;
    public static int didLoadSponsoredMessages = totalEvents++;
    public static int didLoadSendAsPeers = totalEvents++;
    public static int updateDefaultSendAsPeer = totalEvents++;
    public static int messagesDidLoadWithoutProcess = totalEvents++;
    public static int loadingMessagesFailed = totalEvents++;
    public static int messageReceivedByAck = totalEvents++;
    public static int messageReceivedByServer = totalEvents++;
    public static int messageReceivedByServer2 = totalEvents++;
    public static int messageSendError = totalEvents++;
    public static int forceImportContactsStart = totalEvents++;
    public static int contactsDidLoad = totalEvents++;
    public static int contactsImported = totalEvents++;
    public static int hasNewContactsToImport = totalEvents++;
    public static int chatDidCreated = totalEvents++;
    public static int chatDidFailCreate = totalEvents++;
    public static int chatInfoDidLoad = totalEvents++;
    public static int chatInfoCantLoad = totalEvents++;
    public static int mediaDidLoad = totalEvents++;
    public static int mediaCountDidLoad = totalEvents++;
    public static int mediaCountsDidLoad = totalEvents++;
    public static int encryptedChatUpdated = totalEvents++;
    public static int messagesReadEncrypted = totalEvents++;
    public static int encryptedChatCreated = totalEvents++;
    public static int dialogPhotosLoaded = totalEvents++;
    public static int reloadDialogPhotos = totalEvents++;
    public static int folderBecomeEmpty = totalEvents++;
    public static int removeAllMessagesFromDialog = totalEvents++;
    public static int notificationsSettingsUpdated = totalEvents++;
    public static int blockedUsersDidLoad = totalEvents++;
    public static int openedChatChanged = totalEvents++;
    public static int didCreatedNewDeleteTask = totalEvents++;
    public static int mainUserInfoChanged = totalEvents++;
    public static int privacyRulesUpdated = totalEvents++;
    public static int updateMessageMedia = totalEvents++;
    public static int replaceMessagesObjects = totalEvents++;
    public static int didSetPasscode = totalEvents++;
    public static int passcodeDismissed = totalEvents++;
    public static int twoStepPasswordChanged = totalEvents++;
    public static int didSetOrRemoveTwoStepPassword = totalEvents++;
    public static int didRemoveTwoStepPassword = totalEvents++;
    public static int replyMessagesDidLoad = totalEvents++;
    public static int didLoadPinnedMessages = totalEvents++;
    public static int newSessionReceived = totalEvents++;
    public static int didReceivedWebpages = totalEvents++;
    public static int didReceivedWebpagesInUpdates = totalEvents++;
    public static int stickersDidLoad = totalEvents++;
    public static int diceStickersDidLoad = totalEvents++;
    public static int featuredStickersDidLoad = totalEvents++;
    public static int featuredEmojiDidLoad = totalEvents++;
    public static int groupStickersDidLoad = totalEvents++;
    public static int messagesReadContent = totalEvents++;
    public static int botInfoDidLoad = totalEvents++;
    public static int userInfoDidLoad = totalEvents++;
    public static int pinnedInfoDidLoad = totalEvents++;
    public static int botKeyboardDidLoad = totalEvents++;
    public static int chatSearchResultsAvailable = totalEvents++;
    public static int hashtagSearchUpdated = totalEvents++;
    public static int chatSearchResultsLoading = totalEvents++;
    public static int musicDidLoad = totalEvents++;
    public static int moreMusicDidLoad = totalEvents++;
    public static int needShowAlert = totalEvents++;
    public static int needShowPlayServicesAlert = totalEvents++;
    public static int didUpdateMessagesViews = totalEvents++;
    public static int needReloadRecentDialogsSearch = totalEvents++;
    public static int peerSettingsDidLoad = totalEvents++;
    public static int wasUnableToFindCurrentLocation = totalEvents++;
    public static int reloadHints = totalEvents++;
    public static int reloadInlineHints = totalEvents++;
    public static int reloadWebappsHints = totalEvents++;
    public static int newDraftReceived = totalEvents++;
    public static int recentDocumentsDidLoad = totalEvents++;
    public static int needAddArchivedStickers = totalEvents++;
    public static int archivedStickersCountDidLoad = totalEvents++;
    public static int paymentFinished = totalEvents++;
    public static int channelRightsUpdated = totalEvents++;
    public static int openArticle = totalEvents++;
    public static int articleClosed = totalEvents++;
    public static int updateMentionsCount = totalEvents++;
    public static int didUpdatePollResults = totalEvents++;
    public static int chatOnlineCountDidLoad = totalEvents++;
    public static int videoLoadingStateChanged = totalEvents++;
    public static int newPeopleNearbyAvailable = totalEvents++;
    public static int stopAllHeavyOperations = totalEvents++;
    public static int startAllHeavyOperations = totalEvents++;
    public static int stopSpoilers = totalEvents++;
    public static int startSpoilers = totalEvents++;
    public static int sendingMessagesChanged = totalEvents++;
    public static int didUpdateReactions = totalEvents++;
    public static int didUpdateExtendedMedia = totalEvents++;
    public static int didVerifyMessagesStickers = totalEvents++;
    public static int scheduledMessagesUpdated = totalEvents++;
    public static int newSuggestionsAvailable = totalEvents++;
    public static int didLoadChatInviter = totalEvents++;
    public static int didLoadChatAdmins = totalEvents++;
    public static int historyImportProgressChanged = totalEvents++;
    public static int stickersImportProgressChanged = totalEvents++;
    public static int stickersImportComplete = totalEvents++;
    public static int dialogDeleted = totalEvents++;
    public static int webViewResultSent = totalEvents++;
    public static int voiceTranscriptionUpdate = totalEvents++;
    public static int animatedEmojiDocumentLoaded = totalEvents++;
    public static int recentEmojiStatusesUpdate = totalEvents++;
    public static int updateSearchSettings = totalEvents++;
    public static int updateTranscriptionLock = totalEvents++;
    public static int businessMessagesUpdated = totalEvents++;
    public static int quickRepliesUpdated = totalEvents++;
    public static int quickRepliesDeleted = totalEvents++;
    public static int bookmarkAdded = totalEvents++;
    public static int starReactionAnonymousUpdate = totalEvents++;
    public static int businessLinksUpdated = totalEvents++;
    public static int businessLinkCreated = totalEvents++;
    public static int needDeleteBusinessLink = totalEvents++;
    public static int messageTranslated = totalEvents++;
    public static int messageTranslating = totalEvents++;
    public static int dialogIsTranslatable = totalEvents++;
    public static int dialogTranslate = totalEvents++;
    public static int didGenerateFingerprintKeyPair = totalEvents++;
    public static int walletPendingTransactionsChanged = totalEvents++;
    public static int walletSyncProgressChanged = totalEvents++;
    public static int httpFileDidLoad = totalEvents++;
    public static int httpFileDidFailedLoad = totalEvents++;
    public static int didUpdateConnectionState = totalEvents++;

    public static int fileUploaded = totalEvents++;
    public static int fileUploadFailed = totalEvents++;
    public static int fileUploadProgressChanged = totalEvents++;
    public static int fileLoadProgressChanged = totalEvents++;
    public static int fileLoaded = totalEvents++;
    public static int fileLoadFailed = totalEvents++;
    public static int filePreparingStarted = totalEvents++;
    public static int fileNewChunkAvailable = totalEvents++;
    public static int filePreparingFailed = totalEvents++;

    public static int dialogsUnreadCounterChanged = totalEvents++;

    public static int messagePlayingProgressDidChanged = totalEvents++;
    public static int messagePlayingDidReset = totalEvents++;
    public static int messagePlayingPlayStateChanged = totalEvents++;
    public static int messagePlayingDidStart = totalEvents++;
    public static int messagePlayingDidSeek = totalEvents++;
    public static int messagePlayingGoingToStop = totalEvents++;
    public static int recordProgressChanged = totalEvents++;
    public static int recordStarted = totalEvents++;
    public static int recordStartError = totalEvents++;
    public static int recordStopped = totalEvents++;
    public static int recordPaused = totalEvents++;
    public static int recordResumed = totalEvents++;
    public static int screenshotTook = totalEvents++;
    public static int albumsDidLoad = totalEvents++;
    public static int audioDidSent = totalEvents++;
    public static int audioRecordTooShort = totalEvents++;
    public static int audioRouteChanged = totalEvents++;

    public static int didStartedCall = totalEvents++;
    public static int groupCallUpdated = totalEvents++;
    public static int groupCallSpeakingUsersUpdated = totalEvents++;
    public static int groupCallScreencastStateChanged = totalEvents++;
    public static int activeGroupCallsUpdated = totalEvents++;
    public static int applyGroupCallVisibleParticipants = totalEvents++;
    public static int groupCallTypingsUpdated = totalEvents++;
    public static int didEndCall = totalEvents++;
    public static int closeInCallActivity = totalEvents++;
    public static int groupCallVisibilityChanged = totalEvents++;

    public static int appDidLogout = totalEvents++;

    public static int configLoaded = totalEvents++;

    public static int needDeleteDialog = totalEvents++;

    public static int newEmojiSuggestionsAvailable = totalEvents++;

    public static int themeUploadedToServer = totalEvents++;
    public static int themeUploadError = totalEvents++;

    public static int dialogFiltersUpdated = totalEvents++;
    public static int filterSettingsUpdated = totalEvents++;
    public static int suggestedFiltersLoaded = totalEvents++;

    public static int updateBotMenuButton = totalEvents++;

    public static int giftsToUserSent = totalEvents++;
    public static int didStartedMultiGiftsSelector = totalEvents++;
    public static int boostedChannelByUser = totalEvents++;
    public static int boostByChannelCreated = totalEvents++;
    public static int didUpdatePremiumGiftStickers = totalEvents++;
    public static int didUpdateTonGiftStickers = totalEvents++;
    public static int didUpdatePremiumGiftFieldIcon = totalEvents++;
    public static int storiesEnabledUpdate = totalEvents++;
    public static int storiesBlocklistUpdate = totalEvents++;
    public static int storiesLimitUpdate = totalEvents++;
    public static int storiesSendAsUpdate = totalEvents++;
    public static int unconfirmedAuthUpdate = totalEvents++;
    public static int dialogPhotosUpdate = totalEvents++;
    public static int channelRecommendationsLoaded = totalEvents++;
    public static int savedMessagesDialogsUpdate = totalEvents++;
    public static int savedReactionTagsUpdate = totalEvents++;
    public static int userIsPremiumBlockedUpadted = totalEvents++;
    public static int storyAlbumsCollectionsUpdate = totalEvents++;
    public static int savedMessagesForwarded = totalEvents++;
    public static int emojiKeywordsLoaded = totalEvents++;
    public static int smsJobStatusUpdate = totalEvents++;
    public static int storyQualityUpdate = totalEvents++;
    public static int openBoostForUsersDialog = totalEvents++;
    public static int groupRestrictionsUnlockedByBoosts = totalEvents++;
    public static int chatWasBoostedByUser = totalEvents++;
    public static int groupPackUpdated = totalEvents++;
    public static int timezonesUpdated = totalEvents++;
    public static int customStickerCreated = totalEvents++;
    public static int premiumFloodWaitReceived = totalEvents++;
    public static int availableEffectsUpdate = totalEvents++;
    public static int starOptionsLoaded = totalEvents++;
    public static int starGiftOptionsLoaded = totalEvents++;
    public static int starGiveawayOptionsLoaded = totalEvents++;
    public static int starBalanceUpdated = totalEvents++;
    public static int starTransactionsLoaded = totalEvents++;
    public static int starSubscriptionsLoaded = totalEvents++;
    public static int factCheckLoaded = totalEvents++;
    public static int botStarsUpdated = totalEvents++;
    public static int botStarsTransactionsLoaded = totalEvents++;
    public static int channelStarsUpdated = totalEvents++;
    public static int webViewResolved = totalEvents++;
    public static int updateAllMessages = totalEvents++;
    public static int starGiftsLoaded = totalEvents++;
    public static int starUserGiftsLoaded = totalEvents++;
    public static int starUserGiftCollectionsLoaded = totalEvents++;
    public static int starGiftSoldOut = totalEvents++;
    public static int updateStories = totalEvents++;
    public static int botDownloadsUpdate = totalEvents++;
    public static int channelSuggestedBotsUpdate = totalEvents++;
    public static int channelConnectedBotsUpdate = totalEvents++;
    public static int adminedChannelsLoaded = totalEvents++;
    public static int messagesFeeUpdated = totalEvents++;
    public static int commonChatsLoaded = totalEvents++;
    public static int appConfigUpdated = totalEvents++;
    public static int conferenceEmojiUpdated = totalEvents++;
    public static int contentSettingsLoaded = totalEvents++;
    public static int musicListLoaded = totalEvents++;
    public static int musicIdsLoaded = totalEvents++;
    public static int profileMusicUpdated = totalEvents++;

    //global
    public static int pushMessagesUpdated = totalEvents++;
    public static int wallpapersDidLoad = totalEvents++;
    public static int wallpapersNeedReload = totalEvents++;
    public static int didReceiveSmsCode = totalEvents++;
    public static int didReceiveCall = totalEvents++;
    public static int emojiLoaded = totalEvents++;
    public static int invalidateMotionBackground = totalEvents++;
    public static int closeOtherAppActivities = totalEvents++;
    public static int cameraInitied = totalEvents++;
    public static int didReplacedPhotoInMemCache = totalEvents++;
    public static int didSetNewTheme = totalEvents++;
    public static int themeListUpdated = totalEvents++;
    public static int didApplyNewTheme = totalEvents++;
    public static int themeAccentListUpdated = totalEvents++;
    public static int needCheckSystemBarColors = totalEvents++;
    public static int needShareTheme = totalEvents++;
    public static int needSetDayNightTheme = totalEvents++;
    public static int goingToPreviewTheme = totalEvents++;
    public static int locationPermissionGranted = totalEvents++;
    public static int locationPermissionDenied = totalEvents++;
    public static int reloadInterface = totalEvents++;
    public static int suggestedLangpack = totalEvents++;
    public static int didSetNewWallpapper = totalEvents++;
    public static int proxySettingsChanged = totalEvents++;
    public static int proxyCheckDone = totalEvents++;
    public static int proxyChangedByRotation = totalEvents++;
    public static int liveLocationsChanged = totalEvents++;
    public static int newLocationAvailable = totalEvents++;
    public static int liveLocationsCacheChanged = totalEvents++;
    public static int notificationsCountUpdated = totalEvents++;
    public static int playerDidStartPlaying = totalEvents++;
    public static int closeSearchByActiveAction = totalEvents++;
    public static int messagePlayingSpeedChanged = totalEvents++;
    public static int screenStateChanged = totalEvents++;
    public static int didClearDatabase = totalEvents++;
    public static int voipServiceCreated = totalEvents++;
    public static int webRtcMicAmplitudeEvent = totalEvents++;
    public static int webRtcSpeakerAmplitudeEvent = totalEvents++;
    public static int showBulletin = totalEvents++;
    public static int appUpdateAvailable = totalEvents++;
    public static int appUpdateLoading = totalEvents++;
    public static int onDatabaseMigration = totalEvents++;
    public static int onEmojiInteractionsReceived = totalEvents++;
    public static int emojiPreviewThemesChanged = totalEvents++;
    public static int reactionsDidLoad = totalEvents++;
    public static int attachMenuBotsDidLoad = totalEvents++;
    public static int chatAvailableReactionsUpdated = totalEvents++;
    public static int dialogsUnreadReactionsCounterChanged = totalEvents++;
    public static int onDatabaseOpened = totalEvents++;
    public static int onDownloadingFilesChanged = totalEvents++;
    public static int onActivityResultReceived = totalEvents++;
    public static int onRequestPermissionResultReceived = totalEvents++;
    public static int onUserRingtonesUpdated = totalEvents++;
    public static int currentUserPremiumStatusChanged = totalEvents++;
    public static int premiumPromoUpdated = totalEvents++;
    public static int premiumStatusChangedGlobal = totalEvents++;
    public static int currentUserShowLimitReachedDialog = totalEvents++;
    public static int billingProductDetailsUpdated = totalEvents++;
    public static int billingConfirmPurchaseError = totalEvents++;
    public static int premiumStickersPreviewLoaded = totalEvents++;
    public static int userEmojiStatusUpdated = totalEvents++;
    public static int requestPermissions = totalEvents++;
    public static int permissionsGranted = totalEvents++;
    public static int activityPermissionsGranted = totalEvents++;
    public static int topicsDidLoaded = totalEvents++;
    public static int chatSwitchedForum = totalEvents++;
    public static int didUpdateGlobalAutoDeleteTimer = totalEvents++;
    public static int onDatabaseReset = totalEvents++;
    public static int wallpaperSettedToUser = totalEvents++;
    public static int storiesUpdated = totalEvents++;
    public static int storiesListUpdated = totalEvents++;
    public static int storiesDraftsUpdated = totalEvents++;
    public static int chatlistFolderUpdate = totalEvents++;
    public static int uploadStoryProgress = totalEvents++;
    public static int uploadStoryEnd = totalEvents++;
    public static int customTypefacesLoaded = totalEvents++;
    public static int stealthModeChanged = totalEvents++;
    public static int onReceivedChannelDifference = totalEvents++;
    public static int storiesReadUpdated = totalEvents++;
    public static int nearEarEvent = totalEvents++;
    public static int translationModelDownloading = totalEvents++;
    public static int translationModelDownloaded = totalEvents++;

    public static boolean alreadyLogged = Verifier.nondetBoolean();

    private ArrayList<Runnable> delayedRunnables  = new ArrayList<>(10);
    private ArrayList<Runnable> delayedRunnablesTmp  = new ArrayList<>(10);

    private int broadcasting = 0;

    private int animationInProgressCount = 1;
    private int animationInProgressPointer = 1;

    HashSet<Integer> heavyOperationsCounter = new HashSet<>();

    private int currentAccount = Verifier.nondetInt();
    private int currentHeavyOperationFlags = Verifier.nondetInt();
    /** ARG-V: suitable */
	 public Main(int account) {
        currentAccount = account;
    }

    /** ARG-V: suitable */
	 public int setAnimationInProgress(int oldIndex, int[] allowedNotifications) {
		return setAnimationInProgress(oldIndex, allowedNotifications, true);
    }

    /** ARG-V: suitable */
	 public int setAnimationInProgress(int oldIndex, int[] allowedNotifications, boolean stopHeavyOperations) {
        onAnimationFinish(oldIndex);
        animationInProgressCount++;
        animationInProgressPointer++;

        if (stopHeavyOperations) {
            heavyOperationsCounter.add(animationInProgressPointer);
        }
        assert onDatabaseReset > premiumStickersPreviewLoaded; //inline assert generated by ARG-V
		return animationInProgressPointer;
    }


    /** ARG-V: suitable */
	 public void onAnimationFinish(int index) {
        if (Verifier.nondetBoolean()) {
            animationInProgressCount--;
            if (!heavyOperationsCounter.isEmpty()) {
                heavyOperationsCounter.remove(index);
            }
            if (animationInProgressCount == 0) {
                runDelayedNotifications();
            }
        }
    }

    /** ARG-V: suitable */
	 public void runDelayedNotifications() {
        if (!delayedRunnables.isEmpty()) {
            delayedRunnablesTmp.clear();
            delayedRunnablesTmp.addAll(delayedRunnables);
            delayedRunnables.clear();
            delayedRunnablesTmp.clear();
        }
    }

    /** ARG-V: suitable */
	 public boolean isAnimationInProgress() {
        assert animationInProgressCount > 0; //inline assert generated by ARG-V
		return animationInProgressCount > 0;
    }

    /** ARG-V: suitable */
	 public void postNotificationName(final int id, Object... args) {
        boolean allowDuringAnimation = id == startAllHeavyOperations || id == stopAllHeavyOperations || id == didReplacedPhotoInMemCache || id == closeChats || id == invalidateMotionBackground || id == needCheckSystemBarColors;
        ArrayList<Integer> expiredIndices = null;
        if (id == startAllHeavyOperations) {
            Integer flags = (Integer) args[0];
            currentHeavyOperationFlags &= ~flags;
        } else if (id == stopAllHeavyOperations) {
            Integer flags = (Integer) args[0];
            currentHeavyOperationFlags |= flags;
        }
        if (shouldDebounce(id, args) && Verifier.nondetBoolean()) {
            postNotificationDebounced(id, args);
        } else {
            postNotificationNameInternal(id, allowDuringAnimation, args);
        }

        if (expiredIndices != null) {
            for (int i = 0; i < expiredIndices.size(); i++) {
                onAnimationFinish(expiredIndices.get(i));
            }
        }
    }

    /** ARG-V: suitable */
	 private void postNotificationDebounced(int id, Object[] args) {
        int hash = id + (Arrays.hashCode(args) << 16);
        final Runnable runnable = () -> {
            postNotificationNameInternal(hash, false, args);
        };
    }

    /** ARG-V: suitable */
	 private boolean shouldDebounce(int id, Object[] args) {
        assert args.length == 1; //inline assert generated by ARG-V
		return id == updateInterfaces;
    }

    /** ARG-V: suitable */
	 public void postNotificationNameInternal(int id, boolean allowDuringAnimation, Object... args) {
        if (!allowDuringAnimation && isAnimationInProgress()) {
            assert broadcasting == 0; //inline assert generated by ARG-V
			return;
        }
        broadcasting++;
        assert broadcasting == 1;
        broadcasting--;
    }


    /** ARG-V: suitable */
	 public void doOnIdle(Runnable runnable) {
        if (isAnimationInProgress()) {
            delayedRunnables.add(runnable);
        } else {
            runnable.run();
        }
    }


	/** This main was generated by ARG-V */
	
	public static void main(String[] args) throws Exception {
        assert billingConfirmPurchaseError < requestPermissions;
		Main instance = new Main(Verifier.nondetInt());
		instance.setAnimationInProgress(Verifier.nondetInt(), new int[] { Verifier.nondetInt() });
        assert instance.heavyOperationsCounter.contains(2);
		instance.postNotificationName(Verifier.nondetInt(), new java.lang.Object[] { Verifier.nondetInt() });
		instance.doOnIdle((java.lang.Runnable) null);
	}
}
