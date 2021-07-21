import { IImgUrl } from 'app/shared/model/img-url.model';
import { IProcess } from 'app/shared/model/process.model';
import { IBlog } from 'app/shared/model/blog.model';
import { IVideo } from 'app/shared/model/video.model';
import { IPricingCard } from 'app/shared/model/pricing-card.model';

export interface IServiceItem {
  id?: number;
  name?: string;
  description?: string;
  iconName?: string;
  iconId?: number;
  customerImgUrls?: IImgUrl[];
  processes?: IProcess[];
  relatedBlogs?: IBlog[];
  relatedVideos?: IVideo[];
  pricingCards?: IPricingCard[];
  file?: File;
}

export const defaultValue: Readonly<IServiceItem> = {};
